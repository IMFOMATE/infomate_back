package com.pro.infomate.config;

import com.pro.infomate.jwt.JwtAccessDeniedHandler;
import com.pro.infomate.jwt.JwtAuthenticationEntryPoint;
import com.pro.infomate.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider
            , JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
            , JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()

                .antMatchers("/css/**", "/js/**","/images","/lib/**","/productimgs/**","/files/**","/imgs/**");


    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //  개발용 cors 허용
        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            config.addAllowedHeader("*");
            return config;
        });


        // 개발용 csrf 허용
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/").authenticated()
                .antMatchers(HttpMethod.OPTIONS, "*").permitAll()
//                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

}



