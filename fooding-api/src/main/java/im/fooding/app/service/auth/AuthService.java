package im.fooding.app.service.auth;

import feign.FeignException;
import im.fooding.app.dto.request.auth.*;
import im.fooding.app.dto.response.auth.AuthCheckNicknameResponse;
import im.fooding.app.dto.response.auth.AuthUserResponse;
import im.fooding.app.service.file.FileUploadService;
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
import im.fooding.core.model.user.*;
import im.fooding.core.service.user.UserAuthorityService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.List;

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
        userService.update(id, request.getNickname(), request.getPhoneNumber(), request.getGender(), request.getReferralCode(),
                request.isMarketingConsent(), request.getDescription(), request.isPushAgreed(), request.getName());
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
     * 아이디 찾기
     *
     * @param name
     * @param phoneNumber
     * @return String
     */
    public String findUserEmail( String name, String phoneNumber ){

        return null;
    }

    /**
     * 비밀번호 찾기
     * @param email
     * @param name
     * @param phoneNumber
     */
    public void findUserPassword( String email, String name, String phoneNumber ){
        // 해당 사용자가 있는지 확인

        // 해당 사용자가 있는 경우 본인 인증 진행

        // 임시 비밀번호 생성
        
        // 임시 비밀번호 이메일로 전달
        
        // 임시 비밀번호 전달 여부 공지
    }

    /**
     * 본인 인증
     * @param email
     * @param phoneNumber
     * @return boolean
     */
    public boolean certifyUser( String email, String phoneNumber ){
        // 이메일 인증의 경우

        // 전화번호 인증의 경우 --> Pass 인증 등

        return false;
    }
}
