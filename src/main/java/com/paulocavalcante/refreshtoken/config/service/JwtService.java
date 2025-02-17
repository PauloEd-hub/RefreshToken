package com.paulocavalcante.refreshtoken.config.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.paulocavalcante.refreshtoken.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${auth.jwt.token.secret}")
    private String SECRET_KEY;

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(User user, Integer expiration) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withIssuer("auth-api")
                .withSubject(user.getEmail())
                .withExpiresAt(generateExpirationDate(expiration))
                .sign(algorithm);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    public String validateTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (Exception e) {
            return "";
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKeys())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKeys() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Instant generateExpirationDate(Integer expirationToken) {
        return LocalDateTime.now().plusHours(expirationToken).toInstant(ZoneOffset.of("-03:00"));
    }
}
