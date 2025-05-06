package com.pfe.erm.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.annotation.security.PermitAll;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        		.cors().and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers("/user/**")
                .permitAll()
                
                .requestMatchers("/role/**")
                .permitAll()
                .requestMatchers("/category/**")
                .permitAll()
                .requestMatchers("/subcategory/**")
                .permitAll()
                .requestMatchers("/subscription/**")
                .permitAll()
                .requestMatchers("/notification/**")
                .permitAll()
                .requestMatchers("/project/**")
                .permitAll()
                .requestMatchers("/mitigation-plan/**")
                .permitAll()
                .requestMatchers("/topic/**")
                .permitAll()
                .requestMatchers("/password/**")
                .permitAll()
                .requestMatchers("/our-websocket/**")
                .permitAll()
                .requestMatchers("/risk/**")
                .permitAll()
                .requestMatchers("/incident/**")
                .permitAll()
                .requestMatchers("/cause/**")
                .permitAll()
                .requestMatchers("/action/**")
                .permitAll()
                // .hasAnyAuthority("ADMIN","EMPLOYEE","MANAGER")
                .requestMatchers("/impact/**")
                .hasAnyAuthority("ADMIN","EMPLOYEE","MANAGER")
                //.permitAll()
                .requestMatchers("/cause/**")
                .hasAnyAuthority("ADMIN","EMPLOYEE","MANAGER")
                //.permitAll()
                .requestMatchers("/role/retrieve-roles").permitAll()
                //.hasAnyAuthority("ADMIN","EMPLOYEE","MANAGER")
                .requestMatchers("/role/**")
                .hasAnyAuthority("ADMIN")
                //.permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .requiresChannel().requestMatchers("/password/**").requiresSecure()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;
        return http.build();
    }
}
