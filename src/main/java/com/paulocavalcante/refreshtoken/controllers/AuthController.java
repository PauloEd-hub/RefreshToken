package com.paulocavalcante.refreshtoken.controllers;

import com.paulocavalcante.refreshtoken.dtos.AuthDTO;
import com.paulocavalcante.refreshtoken.dtos.RefreshRequestDTO;
import com.paulocavalcante.refreshtoken.dtos.TokenResponseDTO;
import com.paulocavalcante.refreshtoken.dtos.generic.GenericResponse;
import com.paulocavalcante.refreshtoken.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<GenericResponse> auth(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(GenericResponse.success(authService.login(authDTO), "Login successful"));
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<GenericResponse> refreshToken(@RequestBody RefreshRequestDTO refreshToken) {
        return ResponseEntity.ok(GenericResponse.success(authService.refreshToken(refreshToken), "Token was generated"));
    }
}
