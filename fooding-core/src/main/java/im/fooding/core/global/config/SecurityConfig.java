package im.fooding.core.global.config;

import im.fooding.core.global.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
                .sessionManagement((sess) -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers((this.getPermitUrls())).permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/store-coupons").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/stores/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/store-posts/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("/ceo/**", "/app/**", "/pos/**").hasAnyRole("CEO")
                        .requestMatchers(HttpMethod.POST, "/file-upload").hasAnyRole("USER", "ADMIN", "CEO")
                        .anyRequest().permitAll());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
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
                "/auth/apple/token"
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
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
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "OPTIONS", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
