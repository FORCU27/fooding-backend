package im.fooding.app.service.auth;

import feign.FeignException;
import im.fooding.app.dto.request.auth.*;
import im.fooding.app.dto.response.auth.AuthCheckNicknameResponse;
import im.fooding.app.dto.response.auth.AuthUserResponse;
import im.fooding.app.dto.response.ceo.auth.CeoAuthenticatePhoneResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.event.auth.AuthGetResetUrlByEmailEvent;
import im.fooding.core.event.auth.AuthGetResetUrlByPhoneEvent;
import im.fooding.core.event.auth.AuthPhoneAuthenticateEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.feign.client.SocialLoginClient;
import im.fooding.core.global.feign.dto.OauthInfo;
import im.fooding.core.global.feign.dto.google.GoogleUserResponse;
import im.fooding.core.global.feign.dto.kakao.KakaoUserProfile;
import im.fooding.core.global.feign.dto.kakao.KakaoUserResponse;
import im.fooding.core.global.feign.dto.naver.NaverUserProfile;
import im.fooding.core.global.feign.dto.naver.NaverUserResponse;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.global.jwt.service.JwtService;
import im.fooding.core.global.util.AppleLoginUtil;
import im.fooding.core.model.file.File;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.model.user.*;
import im.fooding.core.service.user.AuthenticationService;
import im.fooding.core.service.user.UserAuthorityService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import static im.fooding.core.global.util.Util.isAllowedBackofficeEmails;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final OauthInfo oauthInfo;
    private final SocialLoginClient client;
    private final UserService userService;
    private final UserAuthorityService userAuthorityService;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadService fileUploadService;

    @Value("${message.sender}")
    private String SENDER;
    private final ApplicationEventPublisher publisher;
    private final AuthenticationService authenticationService;

    /**
     * user id로 조회
     *
     * @param id
     * @return AuthUserResponse
     */
    @Transactional(readOnly = true)
    public AuthUserResponse retrieve(long id) {
        return AuthUserResponse.of(userService.findById(id));
    }

    /**
     * 로그인한 유저 정보 수정
     *
     * @param id
     * @param request
     */
    @Transactional
    public void update(long id, AuthUpdateProfileRequest request) {
        userService.update(id, request.getNickname(), request.getPhoneNumber(), request.getGender(), request.getReferralCode(), request.isMarketingConsent(),
                request.getDescription(), request.isPushAgreed(), request.getName(), request.getAddress(), request.getAddressDetail());
    }

    /**
     * 로그인한 유저 프로필 이미지 수정
     *
     * @param id
     * @param imageId(uuid)
     */
    @Transactional
    public void updateProfileImage(long id, String imageId) {
        User user = userService.findById(id);
        String profileImageUrl = null;
        if (StringUtils.hasText(imageId)) {
            File file = fileUploadService.commit(imageId);
            profileImageUrl = file.getUrl();
        }
        userService.updateProfileImage(user, profileImageUrl);
    }

    @Transactional(readOnly = true)
    public AuthCheckNicknameResponse checkDuplicatedNickname(AuthCheckNicknameRequest request) {
        return new AuthCheckNicknameResponse(userService.checkDuplicatedNickname(request.getNickname()));
    }

    /**
     * 일반 회원가입
     *
     * @param request
     */
    @Transactional
    public void register(AuthCreateRequest request) {
        User user = userService.create(request.getEmail(), request.getNickname(), passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(), Gender.NONE, request.getName(), request.getDescription(), request.getReferralCode(), request.isMarketingConsent());
        userAuthorityService.create(user, request.getRole());
    }

    @Transactional
    public TokenResponse refreshToken(AuthRefreshTokenRequest request) {
        return jwtService.refreshToken(request.getRefreshToken());
    }

    /**
     * 일반 로그인
     *
     * @param request
     * @return TokenResponse
     */
    @Transactional
    public TokenResponse login(AuthLoginRequest request) {
        User user = userService.findByEmailAndProvider(request.getEmail(), AuthProvider.FOODING);

        if (user == null) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }

        List<Role> roles = user.getAuthorities().stream()
                .map(UserAuthority::getRole)
                .toList();

        if (!roles.contains(request.getRole())) {
            throw new ApiException(ErrorCode.LOGIN_FAILED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.LOGIN_PASSWORD_MISMATCH);
        }

        TokenResponse tokenResponse = jwtService.issueJwtToken(user.getId());
        user.updatedRefreshToken(tokenResponse.getRefreshToken());
        return tokenResponse;
    }

    /**
     * 소셜 로그인
     *
     * @param request
     * @return TokenResponse
     */
    public TokenResponse loginWithSocial(AuthSocialLoginRequest request) {
        AuthProvider provider = request.getProvider();
        Role role = request.getRole();

        String token = getToken(request.getProvider(), request.getCode(), request.getRedirectUri());

        switch (provider) {
            // TODO: 디자인 패턴 적용
            case KAKAO -> {
                KakaoUserProfile kakaoUserProfile = this.getKakaoUser(token);
                return this.verifyOrRegisterAndIssueToken(kakaoUserProfile.getEmail(), provider, role);
            }
            case GOOGLE -> {
                GoogleUserResponse googleUserResponse = this.getGoogleUser(token);
                return this.verifyOrRegisterAndIssueToken(googleUserResponse.getEmail(), provider, role);
            }
            case NAVER -> {
                NaverUserProfile naverUserProfile = this.getNaverUser(token);
                return this.verifyOrRegisterAndIssueToken(naverUserProfile.getEmail(), provider, role);
            }
            case APPLE -> {
                String email = this.getAppleUser(token);
                return this.verifyOrRegisterAndIssueToken(email, provider, role);
            }
            default -> throw new ApiException(ErrorCode.UNSUPPORTED_SOCIAL);
        }
    }

    /**
     * 토큰 조회
     *
     * @param provider
     * @param code
     * @return String
     */
    private String getToken(AuthProvider provider, String code, String redirectUri) {
        try {
            switch (provider) {
                case KAKAO -> {
                    return client.getKakaoToken(
                            new URI(oauthInfo.getKakaoTokenUri()),
                            oauthInfo.getKakaoRestApiKey(),
                            redirectUri,
                            code,
                            oauthInfo.getGrantType()
                    ).getAccessToken();
                }
                case GOOGLE -> {
                    return client.getGoogleToken(
                            new URI(oauthInfo.getGoogleTokenUri()),
                            oauthInfo.getGoogleClientId(),
                            oauthInfo.getGoogleClientSecret(),
                            redirectUri,
                            code,
                            oauthInfo.getGrantType()
                    ).getAccessToken();
                }
                case NAVER -> {
                    return client.getNaverToken(
                            new URI(oauthInfo.getNaverTokenUri()),
                            oauthInfo.getNaverClientId(),
                            oauthInfo.getNaverClientSecret(),
                            code,
                            oauthInfo.getGrantType()
                    ).getAccessToken();
                }
                case APPLE -> {
                    return client.getAppleToken(
                            new URI(oauthInfo.getAppleTokenUri()),
                            oauthInfo.getAppleClientId(),
                            oauthInfo.getAppleClientSecret(),
                            redirectUri,
                            code,
                            oauthInfo.getGrantType()
                    ).getIdToken();
                }
                default -> throw new ApiException(ErrorCode.UNSUPPORTED_SOCIAL);
            }
        } catch (FeignException e) {
            String detailMessage = e.responseBody().map(bytes -> new String(bytes.array())).orElse(null);
            throw new ApiException(ErrorCode.OAUTH_FAILED, detailMessage);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OAUTH_FAILED, e.getMessage());
        }
    }

    /**
     * 카카오 유저정보 조회
     *
     * @param token
     * @return KakaoAccount
     */
    private KakaoUserProfile getKakaoUser(final String token) {
        try {
            KakaoUserResponse kakaoUserResponse = client.getKakaoUserInfo(new URI(oauthInfo.getKakaoUserInfoUri()), oauthInfo.getTokenType() + token);
            KakaoUserProfile kakaoUserProfile = kakaoUserResponse.getUser();
            if (kakaoUserProfile == null) {
                throw new ApiException(ErrorCode.EMAIL_CONSENT_REQUIRED);
            }
            return kakaoUserProfile;
        } catch (FeignException e) {
            String detailMessage = e.responseBody().map(bytes -> new String(bytes.array())).orElse(null);
            throw new ApiException(ErrorCode.OAUTH_FAILED, detailMessage);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OAUTH_FAILED);
        }
    }

    /**
     * 구글 유저정보 조회
     *
     * @param token
     * @return GoogleAccount
     */
    private GoogleUserResponse getGoogleUser(final String token) {
        try {
            GoogleUserResponse googleUserResponse = client.getGoogleUserInfo(new URI(oauthInfo.getGoogleUserInfoUri()), token);
            if (googleUserResponse.getEmail() == null) {
                throw new ApiException(ErrorCode.EMAIL_CONSENT_REQUIRED);
            }
            return googleUserResponse;
        } catch (FeignException e) {
            String detailMessage = e.responseBody().map(bytes -> new String(bytes.array())).orElse(null);
            throw new ApiException(ErrorCode.OAUTH_FAILED, detailMessage);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OAUTH_FAILED);
        }
    }

    /**
     * 네이버 유저정보 조회
     *
     * @param token
     * @return NaverAccount
     */
    private NaverUserProfile getNaverUser(final String token) {
        try {
            NaverUserResponse naverUserResponse = client.getNaverUserInfo(new URI(oauthInfo.getNaverUserInfoUri()), oauthInfo.getTokenType() + token);
            NaverUserProfile naverUserProfile = naverUserResponse.getUser();
            if (naverUserProfile == null) {
                throw new ApiException(ErrorCode.EMAIL_CONSENT_REQUIRED);
            }
            return naverUserProfile;
        } catch (FeignException e) {
            String detailMessage = e.responseBody().map(bytes -> new String(bytes.array())).orElse(null);
            throw new ApiException(ErrorCode.OAUTH_FAILED, detailMessage);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OAUTH_FAILED);
        }
    }

    /**
     * 애플 이메일 조회
     *
     * @param token
     * @return String
     */
    private String getAppleUser(final String token) {
        try {
            return AppleLoginUtil.parseIdToken(token);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OAUTH_FAILED);
        }
    }

    /**
     * 회원 여부 체크 (신규회원 이라면 회원가입 처리) && jwt 토큰 발급 및 응답
     *
     * @param email
     * @param provider
     * @param role
     * @return TokenResponse
     */
    private TokenResponse verifyOrRegisterAndIssueToken(String email, AuthProvider provider, Role role) {
        if (role == Role.ADMIN && !isAllowedBackofficeEmails(email)) {
            throw new ApiException(ErrorCode.ACCESS_DENIED_EXCEPTION);
        }

        User user = userService.findByEmailAndProvider(email, provider);
        if (user == null) {
            user = userService.createSocialUser(email, provider);
            userAuthorityService.create(user, role);
        } else {
            List<UserAuthority> authorities = user.getAuthorities();
            List<Role> userRoles = authorities.stream().map(UserAuthority::getRole).toList();
            if (!userRoles.contains(role)) {
                userAuthorityService.create(user, role);
            }
        }
        TokenResponse tokenResponse = jwtService.issueJwtToken(user.getId());
        user.updatedRefreshToken(tokenResponse.getRefreshToken());
        return tokenResponse;
    }

    /**
     * 휴대폰으로 인증번호 전송
     *
     * @param name
     * @param phoneNumber
     */
    public void sendAuthenticateCode(String name, String phoneNumber) {
        int code = createAuthCode(phoneNumber);
        sendPhoneAuthentication(name, code, phoneNumber);
    }


    /**
     * 비밀번호 재설정
     *
     * @param encodedLine
     * @param password
     */
    @Transactional
    public void resetPassword(String encodedLine, String password) {
        // 전달한 링크에 담겨 있는 인증 코드 확인
        // Base64로 인코딩한 정보 디코딩
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedLine);
            String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
            System.out.println(decoded);
            String[] valueArray = decoded.split("_");
            String phoneNumber = valueArray[3];
            int code = Integer.parseInt(valueArray[4]);
            User user = userService.findByPhoneNumber(phoneNumber);
            Authentication authentication = authenticationService.findAuthenticationByEmailAndCode(user.getEmail(), code);
            if (authentication == null) return;
            // 기간 만료 확인
            // code를 발급 받고 1시간이 지났다면 기간 만료
            if (LocalDateTime.now().isAfter(authentication.getExpiredAt().plusMinutes(40))) return;

            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(password);

            // 비밀번호 변경
            user.updatePassword(encodedPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 휴대폰 인증 코드 생성
     */
    private int createAuthCode(String phoneNumber) {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        User user = userService.findByPhoneNumber(phoneNumber);
        if (user == null) throw new ApiException(ErrorCode.USER_NOT_FOUND, "가입 정보가 없습니다");
        authenticationService.create(user.getEmail(), phoneNumber, code);
        return code;
    }

    /**
     * 휴대폰 인증 번호 전달
     *
     * @param phoneNumber return String
     */
    private void sendPhoneAuthentication(String name, int code, String phoneNumber) {
        System.out.println(code);
        publisher.publishEvent(new AuthPhoneAuthenticateEvent(name, code, phoneNumber, SENDER, NotificationChannel.SMS));
    }

    /**
     * 휴대폰 인증 번호 확인
     *
     * @param phoneNumber
     * @param code        return boolean
     */
    public CeoAuthenticatePhoneResponse isCorrectCode(String phoneNumber, int code) {
        boolean result = authenticationService.checkCodeAvailable(phoneNumber, code);
        CeoAuthenticatePhoneResponse.CeoAuthenticatePhoneResponseBuilder builder = CeoAuthenticatePhoneResponse.builder();
        if (!result) return builder.isSuccess(false).build();
        User user = userService.findByPhoneNumber(phoneNumber);
        return builder.isSuccess(true).email(user.getEmail()).phoneNumber(user.getPhoneNumber()).code(code).build();
    }

    /**
     * 사용자 이메일 전달
     *
     * @param code
     * @param phoneNumber return String
     */
    public String getEmail(String phoneNumber, int code) {
        User user = userService.findByPhoneNumber(phoneNumber);
        Authentication auth = authenticationService.findAuthenticationByEmailAndCode(user.getEmail(), code);
        if (auth == null) return null;
        return user.getEmail();
    }

    public void sendPasswordResetUrl(String name, String phoneNumber, int code, boolean isEmail) {
        String url = this.createResetUrl(phoneNumber, code);
        User user = userService.findByPhoneNumber(phoneNumber);
        if (!user.getName().equals(name)) throw new ApiException(ErrorCode.AUTHENTICATION_NOT_FOUND, "잘못된 요청입니다");
        if (isEmail) this.sendEmail(name, user.getEmail(), url);
        else this.sendSms(name, user.getPhoneNumber(), url);
    }

    /**
     * 비밀번호 재설정 URL 생성
     *
     * @param phoneNumber // 전화번호
     * @param code        // 휴대폰 인증시 사용했던 6자리 숫자
     *                    return String
     */
    private String createResetUrl(String phoneNumber, int code) {
        // Base64를 통한 사용자 ID + 만료날짜가 담긴 문자열 암호화
        // 임시로 아래처럼 문자열을 사용하지만 자세한 구조는 상의 필요
        String userInformation = "password_reset_url_" + phoneNumber + "_" + code;
        try {
            byte[] bytes = userInformation.getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 이메일 전송
     *
     * @param email
     * @param url
     */
    private void sendEmail(String name, String email, String url) {
        // Google SMTP 활용
        System.out.println(url);
        publisher.publishEvent(new AuthGetResetUrlByEmailEvent(name, email, url, SENDER, NotificationChannel.MAIL));
    }

    /**
     * SMS 전송
     *
     * @param phoneNumber
     * @param url
     */
    private void sendSms(String name, String phoneNumber, String url) {
        // 모든 서비스가 유료. Slack으로 대체
        System.out.println(url);
        publisher.publishEvent(new AuthGetResetUrlByPhoneEvent(name, phoneNumber, url, SENDER, NotificationChannel.SMS));
    }


}
