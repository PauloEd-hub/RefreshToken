package com.paulocavalcante.refreshtoken.services;

import com.paulocavalcante.refreshtoken.config.service.JwtService;
import com.paulocavalcante.refreshtoken.dtos.AuthDTO;
import com.paulocavalcante.refreshtoken.dtos.RefreshRequestDTO;
import com.paulocavalcante.refreshtoken.dtos.TokenResponseDTO;
import com.paulocavalcante.refreshtoken.dtos.exceptions.UnauthorizedException;
import com.paulocavalcante.refreshtoken.entities.User;
import com.paulocavalcante.refreshtoken.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    @Value("${auth.jwt.token.expiration}")
    private int expirationHourToken;

    @Value("${auth.jwt.refresh-token.expiration}")
    private int expirationHourRefreshToken;

    private final UserRepository userRepository;

    private final JwtService jwtService;


    public TokenResponseDTO login(AuthDTO authDTO) {
        User user = userRepository.findByEmail(authDTO.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return TokenResponseDTO.builder()
                .token(jwtService.generateToken(user,expirationHourToken))
                .refreshToken(jwtService.generateToken(user, expirationHourRefreshToken ))
                .build();
    }


    public TokenResponseDTO refreshToken(RefreshRequestDTO refreshRequestDTO) {
        String login = jwtService.validateTokenJwt(refreshRequestDTO.refreshToken());

        User user = userRepository.findByEmail(login).
                orElseThrow(() -> new RuntimeException("User not found"));


        if(user == null) {
            throw new UnauthorizedException("UnauthorizedException");

        }

        var autentication = new UsernamePasswordAuthenticationToken
                (user, null, null);

        SecurityContextHolder.getContext().setAuthentication(autentication);

        return TokenResponseDTO
                .builder()
                .token(jwtService.generateToken(user,expirationHourToken))
                .refreshToken(jwtService.generateToken(user,expirationHourRefreshToken))
                .build();
    }
}
