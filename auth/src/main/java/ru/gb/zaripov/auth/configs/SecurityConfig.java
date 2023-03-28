package ru.gb.zaripov.auth.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import ru.gb.zaripov.auth.entities.User;
import ru.gb.zaripov.auth.services.UserService;

import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            String username = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();

            User user = userService.findUserByName(username)
                    .orElseThrow(()->new BadCredentialsException(String.format("user name '%s' not found", username)));
            if (!passwordEncoder().matches(password, user.getPassword())){
                throw new BadCredentialsException("Wrong password!");
            }
            return new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList())
            );
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/core", "/cart").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
