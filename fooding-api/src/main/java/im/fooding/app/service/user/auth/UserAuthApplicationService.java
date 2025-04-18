package im.fooding.app.service.user.auth;

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
import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthApplicationService {
    private final JwtService jwtService;
    private final OauthInfo oauthInfo;
    private final SocialLoginClient client;
    private final UserService userService;

    /**
     * 소셜 로그인
     *
     * @param provider
     * @param code
     * @return TokenResponse
     */
    @Transactional
    public TokenResponse login(AuthProvider provider, String code, Role role) {
        String token = getToken(provider, code);
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
    private String getToken(AuthProvider provider, String code) {
        try {
            switch (provider) {
                case KAKAO -> {
                    return client.getKakaoToken(
                            new URI(oauthInfo.getKakaoTokenUri()),
                            oauthInfo.getKakaoRestApiKey(),
                            oauthInfo.getKakaoRedirectUri(),
                            code,
                            "authorization_code"
                    ).getAccessToken();
                }
                case GOOGLE -> {
                    return client.getGoogleToken(
                            new URI(oauthInfo.getGoogleTokenUri()),
                            oauthInfo.getGoogleClientId(),
                            oauthInfo.getGoogleClientSecret(),
                            oauthInfo.getGoogleRedirectUri(),
                            code,
                            "authorization_code"
                    ).getAccessToken();
                }
                case NAVER -> {
                    return client.getNaverToken(
                            new URI(oauthInfo.getNaverTokenUri()),
                            oauthInfo.getNaverClientId(),
                            oauthInfo.getNaverClientSecret(),
                            code,
                            "authorization_code"
                    ).getAccessToken();
                }
                case APPLE -> {
                    return client.getAppleToken(
                            new URI(oauthInfo.getAppleTokenUri()),
                            oauthInfo.getAppleClientId(),
                            oauthInfo.getAppleClientSecret(),
                            oauthInfo.getAppleRedirectUri(),
                            code,
                            "authorization_code"
                    ).getIdToken();
                }
                default -> throw new ApiException(ErrorCode.UNSUPPORTED_SOCIAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.LOGIN_FAILED);
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
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
            e.printStackTrace();
            throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
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
        User user = userService.findByEmailOrCreateUser(email, provider, role);
        TokenResponse tokenResponse = jwtService.issueJwtToken(user.getId());
        user.updatedRefreshToken(tokenResponse.getRefreshToken());
        return tokenResponse;
    }
}
