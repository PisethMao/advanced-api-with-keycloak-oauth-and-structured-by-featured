package com.co.istad.piseth.spring_web_mvc.features.userprofile;

import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.PatchUserProfileRequest;
import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse getUserProfile();

    UserProfileResponse patchUserProfile(PatchUserProfileRequest patchUserProfileRequest);
}
