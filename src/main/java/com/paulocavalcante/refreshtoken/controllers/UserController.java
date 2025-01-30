package com.paulocavalcante.refreshtoken.controllers;

import com.paulocavalcante.refreshtoken.dtos.TokenResponseDTO;
import com.paulocavalcante.refreshtoken.dtos.UserRegisterDTO;
import com.paulocavalcante.refreshtoken.dtos.generic.GenericResponse;
import com.paulocavalcante.refreshtoken.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;



    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(@RequestBody UserRegisterDTO userRegisterDTO) {

        return ResponseEntity.ok(GenericResponse.success(userService.register(userRegisterDTO), "User Created"));
    }
}
