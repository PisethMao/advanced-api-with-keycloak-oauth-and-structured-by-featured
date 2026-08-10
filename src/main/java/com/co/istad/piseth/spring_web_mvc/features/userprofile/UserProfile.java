package com.co.istad.piseth.spring_web_mvc.features.userprofile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    private String userId;
    private String profilePicture;
    private String jobTitle;
    private BigDecimal salary;
    private String phoneNumber;
    private String githubLink;
    private String facebookLink;
}
