package stanl_2.weshareyou.global.security.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import stanl_2.weshareyou.global.security.constants.ApplicationConstants;
import stanl_2.weshareyou.global.security.filter.CsrfCookieFilter;
import stanl_2.weshareyou.global.security.filter.JWTTokenGeneratorFilter;
import stanl_2.weshareyou.global.security.filter.JWTTokenValidatorFilter;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProdSecurityConfig {

    private final ApplicationConstants applicationConstants;

    public ProdSecurityConfig(ApplicationConstants applicationConstants) {
        this.applicationConstants = applicationConstants;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //CSRF 토큰 요청 속성을 사용하여 토큰 값을 헤더나 매개변수 값으로 해결하는 로직을 포함
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        config.setAllowedMethods(Collections.singletonList("*"));   // 모든 유형의 http 메소드 트래픽 허용
                        config.setAllowCredentials(true);   // UI에서 백엔드로 user 자격 증명이나 기타 적용 가능한 쿠키 수락
                        config.setAllowedHeaders(Collections.singletonList("*"));   // 모든 종류의 헤더를 수락해도 괜찮다.
                        config.setExposedHeaders(Arrays.asList("Authorization"));   // 헤터를 사용하여 JWT 토큰값 전송
                        config.setMaxAge(3600L);    // 1시간
                        return config;
                    }
                }))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        // 아래 API들에 대해서는 CSRF 보호를 무시하도록 지시(공개)
                        .ignoringRequestMatchers("/api/v1/member/register", "/api/v1/member/login")
                        // 로그인 작업 후 처음으로 CSRF 토큰을 생성하는데만 도움을 준다.
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(applicationConstants), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(applicationConstants), BasicAuthenticationFilter.class)
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
                .authorizeHttpRequests((requests -> requests
//                        .requestMatchers("/userDetail").authenticated()
//                        .requestMatchers("/userDetail").hasRole("MEMBER")
                        .requestMatchers("/api/v1/board").hasRole("MEMBER")
                .requestMatchers("/api/v1/member/register", "/api/v1/member/login").permitAll()
                .anyRequest().authenticated()));
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 사용자 비밀 번호가 유출 되었는지 확인하는 메소드
     * From Spring Security 6.3부터 도입
     * @return
     * */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    // 인증 메커니즘을 시작하는 역할
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        // 인증 제공자 객체
        ProdUsernamePwdAuthenticationProvider authenticationProvider =
                new ProdUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        // provider manager는 Authentication 객체 내부의 비밀번호를 지우지 못하게 설정(유효성 검사)
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

}
