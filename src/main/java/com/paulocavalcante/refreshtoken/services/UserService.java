package com.paulocavalcante.refreshtoken.services;

import com.paulocavalcante.refreshtoken.dtos.TokenResponseDTO;
import com.paulocavalcante.refreshtoken.dtos.UserRegisterDTO;
import com.paulocavalcante.refreshtoken.entities.User;
import com.paulocavalcante.refreshtoken.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public User register(UserRegisterDTO userRegisterDTO) {
        boolean userExists = userRepository.existsByEmail(userRegisterDTO.getEmail());

        if(userExists) {
            return null;
        }

        User user = User.builder()
                .email(userRegisterDTO.getEmail())
                .name(userRegisterDTO.getName())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .build();

        return userRepository.save(user);
    }
}
