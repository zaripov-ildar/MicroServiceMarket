package ru.gb.zaripov.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.zaripov.api.JwtRequest;
import ru.gb.zaripov.api.JwtResponse;
import ru.gb.zaripov.api.exceptions.AppError;
import ru.gb.zaripov.auth.services.UserService;
import ru.gb.zaripov.auth.utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;


    @PostMapping
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) {
        log.info("{} tries to connect",jwtRequest.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException bce) {
            return new ResponseEntity<>(new AppError("BAD_CREDENTIALS", "Wrong password or login"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.getDetailsByName(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
