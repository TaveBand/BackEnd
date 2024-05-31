package ys_band.develop.config;
/*
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ys_band.develop.service.UserService;

import java.io.IOException;
*/
/*
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    // BCryptPasswordEncoder 빈을 생성하여 비밀번호를 암호화하는 데 사용
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 정적 리소스에 대한 요청은 시큐리티 필터를 거치지 않도록 설정
    //Bean
    //public WebSecurityCustomizer webSecurityCustomizer() {
    //    return (web) -> web.ignoring()
    //            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    //}

    // 시큐리티 필터 체인을 구성하는 메서드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 기능을 비활성화
                .csrf(csrf -> csrf.disable())
                // 요청에 대한 권한 설정
                .authorizeHttpRequests(requests -> requests
                        // 루트, 로그인, 회원가입 경로는 인증 없이 접근 가능
                        .requestMatchers("/", "/login", "/register").permitAll()
                        // 그 외의 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // 폼 로그인 설정
                .formLogin(form -> form
                        // 로그인 페이지 설정
                        .loginPage("/login")
                        // 로그인 성공 시 리다이렉트할 기본 경로 설정
                        .defaultSuccessUrl("/home", true)
                        // 로그인 페이지 접근을 모든 사용자에게 허용
                        .permitAll()
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        // 로그아웃 접근을 모든 사용자에게 허용
                        .permitAll());

        // 설정한 시큐리티 필터 체인을 반환
        return http.build();
    }

    // AuthenticationManagerBuilder를 통해 사용자 인증 서비스와 패스워드 인코더를 설정
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // UserService를 사용하여 사용자 인증을 처리하고,
        // BCryptPasswordEncoder를 사용하여 비밀번호를 암호화함
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
*/