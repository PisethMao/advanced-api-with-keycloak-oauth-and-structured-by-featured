package com.co.istad.piseth.spring_web_mvc.features.userprofile;

import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.PatchUserProfileRequest;
import com.co.istad.piseth.spring_web_mvc.features.userprofile.dto.UserProfileResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class UserProfileMapper {
    public UserProfileResponse userProfileToUserProfileResponse(
            UserRepresentation userRepresentation,
            UserProfile userProfile) {
        return UserProfileResponse.builder()
                .userId(userRepresentation.getId())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .email(userRepresentation.getEmail())
                .gender(userRepresentation.getAttributes().get("gender").getFirst())
                .biography(userRepresentation.getAttributes().get("biography").getFirst())
                .profilePicture(userProfile.getProfilePicture())
                .phoneNumber(userProfile.getPhoneNumber())
                .jobTitle(userProfile.getJobTitle())
                .salary(userProfile.getSalary())
                .githubLink(userProfile.getGithubLink())
                .facebookLink(userProfile.getFacebookLink())
                .build();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void toEntity(@MappingTarget UserProfile userProfile, PatchUserProfileRequest patchUserProfileRequest);

    public void toUserRepresentation(UserRepresentation userRepresentation, PatchUserProfileRequest patchUserProfileRequest){
        if (patchUserProfileRequest == null){
            return;
        }
        if (patchUserProfileRequest.firstName() != null) {
            userRepresentation.setFirstName(patchUserProfileRequest.firstName());
        }
        if (patchUserProfileRequest.lastName() != null) {
            userRepresentation.setLastName(patchUserProfileRequest.lastName());
        }
        if (patchUserProfileRequest.gender() != null) {
            userRepresentation.getAttributes().get("gender")
                    .set(0, patchUserProfileRequest.gender());
        }
        if (patchUserProfileRequest.biography() != null) {
            userRepresentation.getAttributes().get("biography")
                    .set(0, patchUserProfileRequest.biography());
        }
    }
}
