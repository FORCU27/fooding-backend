package im.fooding.core.global.feign.client;

import im.fooding.core.global.feign.dto.apple.AppleTokenResponse;
import im.fooding.core.global.feign.dto.google.GoogleTokenResponse;
import im.fooding.core.global.feign.dto.google.GoogleUserResponse;
import im.fooding.core.global.feign.dto.kakao.KakaoTokenResponse;
import im.fooding.core.global.feign.dto.kakao.KakaoUserResponse;
import im.fooding.core.global.feign.dto.naver.NaverTokenResponse;
import im.fooding.core.global.feign.dto.naver.NaverUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "socialLoginClient")
public interface SocialLoginClient {

    @GetMapping
    GoogleUserResponse getGoogleUserInfo(URI baseUrl, @RequestParam("access_token") String accessToken);

    @PostMapping
    GoogleTokenResponse getGoogleToken(URI baseUrl,
                                       @RequestParam("client_id") String clientId,
                                       @RequestParam("client_secret") String clientSecret,
                                       @RequestParam("redirect_uri") String redirectUri,
                                       @RequestParam("code") String code,
                                       @RequestParam("grant_type") String grantType
    );

    @GetMapping
    KakaoUserResponse getKakaoUserInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);

    @GetMapping
    KakaoTokenResponse getKakaoToken(URI baseUrl,
                                     @RequestParam("client_id") String restApiKey,
                                     @RequestParam("redirect_uri") String redirectUri,
                                     @RequestParam("code") String code,
                                     @RequestParam("grant_type") String grantType
    );

    @GetMapping
    NaverUserResponse getNaverUserInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);

    @GetMapping
    NaverTokenResponse getNaverToken(URI baseUrl,
                                     @RequestParam("client_id") String clientId,
                                     @RequestParam("client_secret") String clientSecret,
                                     @RequestParam("code") String code,
                                     @RequestParam("grant_type") String grantType
    );

    @PostMapping
    AppleTokenResponse getAppleToken(
            URI baseUrl,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("grant_type") String grantType
    );
}
