package im.fooding.realtime.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    //private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable))
                .authorizeExchange(exchanges -> exchanges
                        // 공개 엔드포인트
//                        .pathMatchers("/public/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/user/store-coupons").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/user/banners/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/user/stores/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/user/store-posts/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/user/regions/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/user/recommend-keywords").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/ceo/posts", "/ceo/posts/**").permitAll()
//                        .pathMatchers(HttpMethod.POST, "/app/rewards/**").permitAll()
//                        .pathMatchers(HttpMethod.POST, "/app/devices/**").permitAll()
//                        // 역할 기반 접근 제어
//                        .pathMatchers("/user/**").hasRole("USER")
//                        .pathMatchers("/admin/**").hasRole("ADMIN")
//                        .pathMatchers("/ceo/**", "/app/**", "/pos/**").hasRole("CEO")
//                        .pathMatchers(HttpMethod.POST, "/file-upload").hasAnyRole("USER", "ADMIN", "CEO")
//                        .pathMatchers("/auth/**").hasAnyRole("USER", "ADMIN", "CEO")
                        // 기본 설정
                        .anyExchange()
                        .permitAll()
                )
                .build();
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private String[] getPermitUrls() {
        return new String[]{
                "/ping",
                "/swagger-ui/**",
                "/api-docs/**",
                "/auth/register",
                "/auth/login",
                "/auth/social-login",
                "/auth/google/token",
                "/auth/kakao/token",
                "/auth/naver/token",
                "/auth/apple/token",
                "/auth/nickname/check"
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of(
                        // NOTE: 로컬 개발 허용(정규식?)
                        "http://localhost:3000",
                        "http://localhost:3001",
                        "http://localhost:3002",
                        "http://localhost:3003",
                        // NOTE: 서비스 허용
                        "https://fooding.im",
                        "https://stage.fooding.im",
                        "https://back-office-stage.fooding.im",
                        "https://back-office.fooding.im",
                        "https://app.fooding.im",
                        "https://app-stage.fooding.im",
                        "https://pos.fooding.im",
                        "https://pos-stage.fooding.im",
                        "https://place.fooding.im",
                        "https://place-stage.fooding.im",
                        "https://ceo.fooding.im",
                        "https://ceo-stage.fooding.im",
                        "https://appleid.apple.com"
                ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "OPTIONS", "PUT", "DELETE"));;
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOriginPatterns(List.of("*"));
//        corsConfig.setMaxAge(3600L);
//        corsConfig.addAllowedMethod("*");
//        corsConfig.addAllowedHeader("*");
//        corsConfig.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return new CorsWebFilter(source);
//    }
}
