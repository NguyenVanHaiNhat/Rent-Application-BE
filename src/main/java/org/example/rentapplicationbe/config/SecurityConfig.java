package org.example.rentapplicationbe.config;

import org.example.rentapplicationbe.config.jwt.CustomAccessDeniedHandler;
import org.example.rentapplicationbe.config.jwt.JwtAuthenticationTokenFilter;
import org.example.rentapplicationbe.config.jwt.RestAuthenticationEntryPoint;
import org.example.rentapplicationbe.config.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;

    private String allowOrigins = "http://localhost:3000, http://localhost:8080";

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        String s = "ss";
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authenticationProvider;
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        String[] allowOriginsArr = allowOrigins.split(",");
        List<String> allowOrigins = Arrays.asList(allowOriginsArr);
        configuration.setAllowedOriginPatterns(allowOrigins);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors((httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        }));
        return http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/account/login").permitAll()
                        .requestMatchers("/account/registerr/**").permitAll()
                        .requestMatchers("/account/registerr/**").permitAll()
                        .requestMatchers("/account/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/account-dto/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/account/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/account/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/bookAHouse/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/bookAHouse/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/history-booking/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/history-booking/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house/ownerAvailable/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/house/ownerAvailable/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house/ownerMaintenance/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/house/ownerMaintenance/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house/ownerRented/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/house/ownerRented/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house/owner/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/house/owner/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/house/detail/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/house/detail/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house/post-house/**").hasAuthority("ROLE_HOST")
                        .requestMatchers("/api/house/post-house/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/image/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/image/**").hasAuthority("ROLE_HOST")

                )

                .exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
