package com.quentin.eazyschool.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain projectSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                        .ignoringRequestMatchers("/contact/saveMsg")
                        .ignoringRequestMatchers(PathRequest.toH2Console()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/contact/saveMsg").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/dashboard").authenticated()
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
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user1")
                .password("{noop}password1")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
