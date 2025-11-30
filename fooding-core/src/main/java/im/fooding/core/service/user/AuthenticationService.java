package im.fooding.core.service.user;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.Authentication;
import im.fooding.core.repository.user.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationRepository repository;

    @Transactional
    public void create( String email, String phoneNumber, int code ){
        // 진행되지 않은 인증 or 만료되지 않은 인증 모두 삭제
        List<Authentication> expiredAuthentications = repository.list( email, phoneNumber, 0, false );
        expiredAuthentications.forEach(Authentication::success);
        // 새로운 인증 생성
        Authentication authentication = Authentication.builder()
                .email( email )
                .phoneNumber( phoneNumber )
                .code( code )
                .build();
        repository.save( authentication );
    }

    @Transactional
    public boolean checkCodeAvailable( String phoneNumber, int code ){
        // 인증 정보 확인
        if( repository.list( null, phoneNumber, code, false ).isEmpty() ) throw new ApiException( ErrorCode.AUTHENTICATION_CODE_INCORRECT );
        List<Authentication> authentications = repository.list( null, phoneNumber, code, false );
        // 0개일 경우 = 인증 정보가 없음 ( 시도된 인증이 없음 )
        // 2개 이상일 경우 = 새로운 인증 생성 단계에서 처리되지 않은 인증 존재
        if( authentications.isEmpty() ) throw new ApiException( ErrorCode.AUTHENTICATION_NOT_FOUND );
        else if( authentications.size() > 1 ) throw new ApiException( ErrorCode.AUTHENTICATION_INVALID_ERROR );

        Authentication authentication = authentications.get(0);
        if( authentication == null ) return false;
        // 만료 시간 확인
        if( authentication.getExpiredAt().isBefore(LocalDateTime.now() ) ) return false;
        // 인증 정보 확인 완료 표시
        authentication.success();

        return true;
    }

    public Authentication findAuthenticationByEmailAndCode( String email, int code ){
        List<Authentication> result = repository.list( email, null, code, true );
        if( result.isEmpty() ) throw new ApiException( ErrorCode.AUTHENTICATION_CODE_INCORRECT );
        return result.get( result.size() - 1 );
    }
}
