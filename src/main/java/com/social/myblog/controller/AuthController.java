package com.social.myblog.controller;


import com.social.myblog.model.LoginRequest;
import com.social.myblog.model.LoginResponse;
import com.social.myblog.model.User;
import com.social.myblog.repository.UserRepo;
import com.social.myblog.security.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtiuser;
    private final UserRepo userrepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userrepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!user.get().getPassword().equals(request.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var token = jwtiuser.issue(1, request.getUsername(), List.of("USER"));
        return new ResponseEntity<>(LoginResponse.builder()
                .accessToken(token)
                .build(), HttpStatus.OK);

    }

    @GetMapping("/secured")
    public String secured() {
        return "Logged";
    }
}
