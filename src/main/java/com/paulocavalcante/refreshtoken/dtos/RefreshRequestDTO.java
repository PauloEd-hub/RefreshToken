package com.paulocavalcante.refreshtoken.dtos;

import lombok.Builder;

@Builder
public record RefreshRequestDTO(String refreshToken){
}
