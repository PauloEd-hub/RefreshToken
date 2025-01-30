package com.paulocavalcante.refreshtoken.dtos;

import lombok.Builder;

@Builder
public record AuthDTO(String email, String password) {
}
