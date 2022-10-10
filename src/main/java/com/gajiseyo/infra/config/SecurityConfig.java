package com.gajiseyo.infra.config;

import com.gajiseyo.infra.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //        http.requiresChannel().anyRequest().requiresSecure();

        http.authorizeRequests()
                .mvcMatchers(
                        "/",
                        "/login",
                        "/livereload.js",
                        "/.well-known/**"
                ).permitAll()
                .mvcMatchers(
                        "/items/**",
                        "/chat/**",
                        "/topic/**",
                        "/app/**",
                        "/gs-guide-websocket/**",
                        "/myInfo/**"
                ).hasAnyRole("USER", "ADMIN")
                .anyRequest().denyAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/orders/form");

        http.oauth2Login()
                .loginPage("/oauth")
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http.httpBasic();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return web -> web.ignoring()
                .mvcMatchers(
                        "/node_modules/**",
                        "/AdminLTE-3.1.0/**",
                        "/js/**",
                        "/css/**",
                        "/images/**"
                )
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
