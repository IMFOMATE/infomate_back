package com.pro.infomate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/css/**", "/js/**","/images","/lib/**","/productimgs/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        //  개발용 cors 허용
        http.cors().configurationSource(request -> {
            CorsConfiguration config =  new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
            config.addAllowedHeader("*");
            return config;
        });
//        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//        http.cors().configurationSource(corsConfigurationSource());

        // 개발용 csrf 허용
        http.csrf().disable().authorizeHttpRequests().antMatchers("*").permitAll();


        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
