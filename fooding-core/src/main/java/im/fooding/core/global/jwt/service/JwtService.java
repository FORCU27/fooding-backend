package im.fooding.core.global.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import im.fooding.core.global.CustomUserDetailsService;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String CLAIM = "user";
    private static final String BEARER = "Bearer ";

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public String createAccessToken(Long id) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(generateExpiresAt(accessTokenExpirationPeriod))
                .withClaim(CLAIM, id)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(generateExpiresAt(refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public TokenResponse issueJwtToken(long id) {
        return TokenResponse.builder()
                .accessToken(createAccessToken(id))
                .refreshToken(createRefreshToken())
                .expiredIn(accessTokenExpirationPeriod)
                .refreshExpiredIn(refreshTokenExpirationPeriod)
                .build();
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<Long> extractId(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey)).build()
                    .verify(accessToken)
                    .getClaim(CLAIM)
                    .asLong());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    public void saveAuthentication(long id) {
        UserInfo userInfo = (UserInfo) userDetailsService.loadUserByUserId(id);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, null,
                authoritiesMapper.mapAuthorities(userInfo.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public TokenResponse refreshToken(HttpServletRequest request) {
        String refreshToken = extractRefreshToken(request)
                .filter(this::isTokenValid)
                .orElse(null);
        if (refreshToken != null) {
            User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new ApiException(ErrorCode.REFRESH_TOKEN_INVALID));
            TokenResponse tokenResponse = issueJwtToken(user.getId());
            user.updatedRefreshToken(tokenResponse.getRefreshToken());
            return tokenResponse;
        } else {
            throw new ApiException(ErrorCode.REFRESH_TOKEN_FAILED);
        }
    }

    private Date generateExpiresAt(long tokenExpirationPeriod) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + tokenExpirationPeriod);
        return expiresAt;
    }
}
