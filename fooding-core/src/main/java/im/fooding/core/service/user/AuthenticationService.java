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

    public void create( String email, String phoneNumber, int code ){
        // 진행되지 않은 인증 or 만료되지 않은 인증 모두 삭제
        List<Authentication> expiredAuthentications = repository.findAllByEmailAndPhoneNumber( email, phoneNumber );
        expiredAuthentications.forEach(Authentication::success);
        // 새로운 인증 생성
        Authentication authentication = Authentication.builder()
                .email( email )
                .code( code )
                .build();
        repository.save( authentication );
    }

    @Transactional
    public boolean checkCodeAvailable( String phoneNumber, int code ){
        // 인증 정보 확인
        Authentication authentication = repository.findByPhoneNumberAndCodeAndIsSuccessFalse( phoneNumber, code ).orElseThrow(
                () -> new ApiException(ErrorCode.AUTHENTICATION_NOT_FOUND, "인증 정보가 올바르지 않습니다." )
        );
        // 만료 시간 확인
        if( authentication.getExpiredAt().isBefore(LocalDateTime.now() ) ) return false;
        // 인증 정보 확인 완료 표시
        authentication.success();

        return true;
    }
}
