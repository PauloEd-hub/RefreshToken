package com.paulocavalcante.refreshtoken.dtos;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String token, String refreshToken) {
}
