package com.co.istad.piseth.spring_web_mvc.features.userprofile;

import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.PatchUserProfileRequest;
import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-profiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "keycloak")
public class UserController {
    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public UserProfileResponse getCurrentUser() {
        return userProfileService.getUserProfile();
    }

    @PatchMapping("/me")
    public UserProfileResponse patchUserProfile(@RequestBody
                                                PatchUserProfileRequest patchUserProfileRequest) {
        return userProfileService.patchUserProfile(patchUserProfileRequest);
    }
}
