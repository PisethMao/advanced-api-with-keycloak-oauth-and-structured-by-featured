package com.co.istad.piseth.spring_web_mvc.features.userprofile.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserProfileResponse(
        String userId,
        String email,
        String firstName,
        String lastName,
        String gender,
        String biography,
        String profilePicture,
        String jobTitle,
        BigDecimal salary,
        String phoneNumber,
        String githubLink,
        String facebookLink
) {
}
