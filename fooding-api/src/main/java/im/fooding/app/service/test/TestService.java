package im.fooding.app.service.test;

import im.fooding.app.dto.request.auth.AuthCreateRequest;
import im.fooding.core.model.user.Gender;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final StoreService storeService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 테스트를 위한 더미 가게 생성 API
    public void createDummyStores( int count ){
        // Dummy CEO
        Random random = new Random();
        int randomPhoneNumber = 10000000 + random.nextInt(90000000);
        String dummyPhoneNumber = "010" + Integer.toString( randomPhoneNumber );
        String dummyEmail = Integer.toString( randomPhoneNumber ) + "@gmail.com";
        String password = "1234";
        String name = "사용자" + Integer.toString( randomPhoneNumber );
        String dummyDescription = "테스트용 더미 사용자" + Integer.toString( randomPhoneNumber );

        AuthCreateRequest request = new AuthCreateRequest();
        request.setEmail( dummyEmail );
        request.setPassword( password );
        request.setRole(Role.CEO );
        request.setName( name );
        request.setDescription( dummyDescription );
        request.setPhoneNumber( dummyPhoneNumber );
        request.setNickname( name );
        request.setMarketingConsent( false );

        User dummyUser = userService.create(request.getEmail(), request.getNickname(), passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(), Gender.NONE, request.getName(), request.getDescription(), request.getReferralCode(), request.isMarketingConsent());

        for( int i=0; i<count; i++ ){

        }

    }

}
