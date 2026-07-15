package com.co.istad.piseth.spring_web_mvc.features.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String keycloakUserId,
        String username,
        String email,
        String firstName,
        String lastname,
        String phoneNumber,
        String gender,
        String biography
) {
}
