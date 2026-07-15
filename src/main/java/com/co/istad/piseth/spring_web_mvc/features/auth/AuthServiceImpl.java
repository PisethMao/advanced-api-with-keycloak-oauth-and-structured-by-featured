package com.co.istad.piseth.spring_web_mvc.features.auth;

import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterRequest;
import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterResponse;
import com.co.istad.piseth.spring_web_mvc.security.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match");
        }
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastname());
        user.setEnabled(true);
        user.setEmailVerified(false);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("gender", List.of(registerRequest.gender()));
        attributes.put("biography", List.of(registerRequest.biography()));
        attributes.put("phoneNumber", List.of(registerRequest.phoneNumber()));
        user.setAttributes(attributes);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.password());
        user.setCredentials(List.of(credential));
        UsersResource usersResource = keycloak
                .realm(keycloakProperties.getRealm())
                .users();
        try (Response response = usersResource.create(user)) {
            log.info("Response status code: {}", response.getStatus());
            if (response.getStatus() == HttpStatus.CREATED.value()) {
                UserRepresentation userRepresentation = usersResource.search(user.getUsername()).getFirst();
                log.info("Created user {}", userRepresentation.getId());
                return RegisterResponse.builder()
                        .keycloakUserId(userRepresentation.getId())
                        .username(userRepresentation.getUsername())
                        .email(userRepresentation.getEmail())
                        .firstName(userRepresentation.getFirstName())
                        .lastname(userRepresentation.getLastName())
                        .phoneNumber(userRepresentation.firstAttribute("phoneNumber"))
                        .gender(userRepresentation.firstAttribute("gender"))
                        .biography(userRepresentation.firstAttribute("biography"))
                        .build();
            }
        }
        return null;
    }
}
