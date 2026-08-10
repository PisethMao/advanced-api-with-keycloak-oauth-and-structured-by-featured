package com.co.istad.piseth.spring_web_mvc.features.auth;

import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterRequest;
import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterResponse;
import com.co.istad.piseth.spring_web_mvc.features.userprofile.UserProfile;
import com.co.istad.piseth.spring_web_mvc.features.userprofile.UserProfileRepository;
import com.co.istad.piseth.spring_web_mvc.security.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final UserProfileRepository userProfileRepository;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (!Objects.equals(request.password(), request.confirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Passwords do not match"
            );
        }

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastname());
        user.setEnabled(true);
        user.setEmailVerified(false);

        Map<String, List<String>> attributes = new HashMap<>();

        putAttributeIfPresent(attributes, "gender", request.gender());
        putAttributeIfPresent(attributes, "biography", request.biography());
        putAttributeIfPresent(attributes, "phoneNumber", request.phoneNumber());

        user.setAttributes(attributes);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);

        user.setCredentials(List.of(credential));

        UsersResource usersResource = keycloak
                .realm(keycloakProperties.getRealm())
                .users();

        try (Response response = usersResource.create(user)) {

            int status = response.getStatus();
            log.info("Keycloak user creation status: {}", status);

            if (status == HttpStatus.CREATED.value()) {
                String keycloakUserId = CreatedResponseUtil.getCreatedId(response);

                UserRepresentation createdUser = usersResource
                        .get(keycloakUserId)
                        .toRepresentation();

                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(keycloakUserId);
                userProfileRepository.save(userProfile);

                return RegisterResponse.builder()
                        .keycloakUserId(createdUser.getId())
                        .username(createdUser.getUsername())
                        .email(createdUser.getEmail())
                        .firstName(createdUser.getFirstName())
                        .lastname(createdUser.getLastName())
                        .phoneNumber(createdUser.firstAttribute("phoneNumber"))
                        .gender(createdUser.firstAttribute("gender"))
                        .biography(createdUser.firstAttribute("biography"))
                        .build();
            }

            if (status == HttpStatus.CONFLICT.value()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Username or email already exists"
                );
            }

            String keycloakError;

            try {
                keycloakError = response.readEntity(String.class);
            } catch (Exception exception) {
                keycloakError = "No error body returned by Keycloak";
            }

            log.error(
                    "Keycloak user creation failed. Status: {}, response: {}",
                    status,
                    keycloakError
            );

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Failed to create user in Keycloak"
            );
        }
    }

    private void putAttributeIfPresent(
            Map<String, List<String>> attributes,
            String attributeName,
            String value
    ) {
        if (StringUtils.hasText(value)) {
            attributes.put(attributeName, List.of(value.trim()));
        }
    }
}