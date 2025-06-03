package com.quentin.eazyschool.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/dashboard").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password1")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
