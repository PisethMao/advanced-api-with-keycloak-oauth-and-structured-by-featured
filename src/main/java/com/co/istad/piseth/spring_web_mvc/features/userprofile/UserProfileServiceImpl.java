package com.co.istad.piseth.spring_web_mvc.features.userprofile;

import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.PatchUserProfileRequest;
import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.UserProfileResponse;
import com.co.istad.piseth.spring_web_mvc.security.AuthUtils;
import com.co.istad.piseth.spring_web_mvc.security.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse getUserProfile() {
        String userId = AuthUtils.extractUserId();
        UserResource userResource = keycloak.realm(keycloakProperties.getRealm())
                .users().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        log.info("User profile: {}", userRepresentation);
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));
        return userProfileMapper.userProfileToUserProfileResponse(userRepresentation, userProfile);
    }

    @Override
    public UserProfileResponse patchUserProfile(PatchUserProfileRequest patchUserProfileRequest) {
        String userId = AuthUtils.extractUserId();
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));
        userProfileMapper.toEntity(userProfile, patchUserProfileRequest);
        userProfileRepository.save(userProfile);
        UserResource userResource = keycloak.realm(keycloakProperties.getRealm())
                .users().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userProfileMapper.toUserRepresentation(userRepresentation, patchUserProfileRequest);
        userResource.update(userRepresentation);
        return userProfileMapper.userProfileToUserProfileResponse(userRepresentation, userProfile);
    }
}
