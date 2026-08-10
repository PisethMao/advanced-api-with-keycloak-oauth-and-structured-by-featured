package com.co.istad.piseth.spring_web_mvc.features.userprofile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}
