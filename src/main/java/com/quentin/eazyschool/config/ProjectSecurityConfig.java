package com.quentin.eazyschool.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain projectSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                        .ignoringRequestMatchers("/contact/saveMsg")
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                        .ignoringRequestMatchers("/public/**"))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/", "/home", "/public/**",
                                "/contact", "/about", "/holidays/**", "/courses",
                                "/contact/saveMsg",
                                "/login", "/logout",
                                "/assets/**")
                        .permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(
                                "/dashboard", "/profile"
                        ).authenticated()
                        .requestMatchers("/contact/displayMessages").hasRole("ADMIN")
                        .requestMatchers("/contact/closeMsg/**").hasRole("ADMIN")
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll())
                .httpBasic(withDefaults())
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
