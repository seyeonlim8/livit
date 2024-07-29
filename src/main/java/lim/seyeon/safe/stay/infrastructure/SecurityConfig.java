package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.application.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //REST API를 사용하는 경우, 특히 JWT와 같은 토큰 기반 인증 방식을 사용할 때, CSRF 보호를 비활성화하는 것이 일반적
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login", "/home").permitAll()
                        .requestMatchers("/houses/**").permitAll()
                        .requestMatchers("/reviews/**").permitAll()
                        .requestMatchers("/api/crime-datas/**").permitAll()
                        .requestMatchers("/api/areas/**").permitAll()
                        .requestMatchers("/api/neighborhoods/**").permitAll()
                        .requestMatchers("/api/user-detail/**").permitAll()
                        .requestMatchers("/api/posts/**").permitAll()
                        .requestMatchers("/api/photos/**").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()
                        .requestMatchers("/api/comments/**").permitAll()
                        .requestMatchers("/roommate-preferences/**").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/houses", "/roommate-search", "/community").authenticated()
                        .requestMatchers("/contact/send").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // 로그인 페이지 URL 지정
                        .loginPage("/login")
                        // 로그인 자격 증명 제출 URL 지정
                        .loginProcessingUrl("/login")
                        // 로그인 성공시 리디렉션할 URL 지정
                        .defaultSuccessUrl("/home", true)
                        // 모든 사용자에게 로그인 페이지와 로그인 자격 증명 제출 접근 허용
                        .permitAll()
                )
                .logout(logout -> logout
                        // 로그아웃 시 HTTP 세션 무효화
                        .invalidateHttpSession(true)
                        // 인증 정보 제거
                        .clearAuthentication(true)
                        // 로그아웃을 트리거할 URL 지정
                        .logoutUrl("/logout")
                        // 로그아웃 성공시 리디렉션할 URL 지정
                        .logoutSuccessUrl("/login?logout")
                        // 모든 사용자에게 로그아웃 URL 접근 허용
                        .permitAll()
                );

        // 구성된 SecurityFilterChain을 빌드하고 반환.
        return http.build();
    }
}
