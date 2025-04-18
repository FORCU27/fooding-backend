package im.fooding.core.global.feign.dto;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.util.AppleLoginUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OauthInfo {
    private final String tokenType = "Bearer ";
    private final String grantType = "authorization_code";

    private final String googleClientId;
    private final String googleClientSecret;
    private final String googleTokenUri;
    private final String googleUserInfoUri;
    private final String googleRedirectUri;

    private final String kakaoRestApiKey;
    private final String kakaoTokenUri;
    private final String kakaoUserInfoUri;
    private final String kakaoRedirectUri;

    private final String naverClientId;
    private final String naverClientSecret;
    private final String naverTokenUri;
    private final String naverUserInfoUri;
    private final String naverRedirectUri;

    private final String appleClientId;
    private final String appleTeamId;
    private final String appleKeyId;
    private final String applePrivateKey;
    private final String appleTokenUri;
    private final String appleRedirectUri;

    public OauthInfo(@Value("${auth.google.client-id:null}") String googleClientId,
                     @Value("${auth.google.client-secret:null}") String googleClientSecret,
                     @Value("${auth.google.token-uri:null}") String googleTokenUri,
                     @Value("${auth.google.userinfo-uri:null}") String googleUserInfoUri,
                     @Value("${auth.google.redirect-uri:null}") String googleRedirectUri,
                     @Value("${auth.kakao.restapi-key:null}") String kakaoRestApiKey,
                     @Value("${auth.kakao.token-uri:null}") String kakaoTokenUri,
                     @Value("${auth.kakao.user-info-uri:null}") String kakaoUserInfoUri,
                     @Value("${auth.kakao.redirect-uri:null}") String kakaoRedirectUri,
                     @Value("${auth.naver.client-id:null}") String naverClientId,
                     @Value("${auth.naver.client-secret:null}") String naverClientSecret,
                     @Value("${auth.naver.token-uri:null}") String naverTokenUri,
                     @Value("${auth.naver.user-info-uri:null}") String naverUserInfoUri,
                     @Value("${auth.naver.redirect-uri:null}") String naverRedirectUri,
                     @Value("${auth.apple.client-id:null}") String appleClientId,
                     @Value("${auth.apple.team-id:null}") String appleTeamId,
                     @Value("${auth.apple.key-id:null}") String appleKeyId,
                     @Value("${auth.apple.private-key:null}") String applePrivateKey,
                     @Value("${auth.apple.token-uri:null}") String appleTokenUri,
                     @Value("${auth.apple.redirect-uri:null}") String appleRedirectUri
    ) {
        this.googleClientId = googleClientId;
        this.googleClientSecret = googleClientSecret;
        this.googleTokenUri = googleTokenUri;
        this.googleUserInfoUri = googleUserInfoUri;
        this.googleRedirectUri = googleRedirectUri;
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.kakaoTokenUri = kakaoTokenUri;
        this.kakaoUserInfoUri = kakaoUserInfoUri;
        this.kakaoRedirectUri = kakaoRedirectUri;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
        this.naverTokenUri = naverTokenUri;
        this.naverUserInfoUri = naverUserInfoUri;
        this.naverRedirectUri = naverRedirectUri;
        this.appleClientId = appleClientId;
        this.appleTeamId = appleTeamId;
        this.appleKeyId = appleKeyId;
        this.applePrivateKey = applePrivateKey;
        this.appleTokenUri = appleTokenUri;
        this.appleRedirectUri = appleRedirectUri;
    }

    public String getAppleClientSecret() {
        try {
            return AppleLoginUtil.generateClientSecret(appleTeamId, appleClientId, appleKeyId, applePrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
