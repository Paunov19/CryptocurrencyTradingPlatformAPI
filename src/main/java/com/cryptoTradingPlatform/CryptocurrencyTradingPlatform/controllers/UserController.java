package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.controllers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.payload.LoginRequest;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.payload.UserRequest;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.security.jwt.JwtResponse;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.security.jwt.JwtUtils;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.security.services.UserDetailsImpl;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @Operation(
            summary = "Register a new user",
            description = "This endpoint allows a new user to register by providing their details such as name, email, and password. " +
                    "The user will be registered and can then log in to the system."
    )
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRequest userRequest) {
        UserDTO user = userService.register(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login an existing user",
            description = "This endpoint allows an existing user to log in by providing their email and password. " +
                    "If the credentials are correct, the user will receive a JWT token to authenticate future requests."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getEmail(),
                    userDetails.getUserName()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong email or password");
        }
    }

    @Operation(
            summary = "Reset an existing user account",
            description = "This endpoint allows an existing user to reset their account. " +
                    "This will delete their portfolio and reset the account to its initial state."
    )
    @PostMapping("/reset-account")
    public ResponseEntity<String> resetAccount() {
        try {
            userService.resetAccount();
            return ResponseEntity.ok("Account reset successfully");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account reset NOT successfully");
        }
    }
}