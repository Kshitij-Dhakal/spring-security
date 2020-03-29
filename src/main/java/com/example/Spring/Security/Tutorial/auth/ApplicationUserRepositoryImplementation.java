package com.example.Spring.Security.Tutorial.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.Spring.Security.Tutorial.security.ApplicationUserRole.*;

@Repository("Impl")
public class ApplicationUserRepositoryImplementation implements ApplicationUserRepository {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserRepositoryImplementation(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApplicationUser findByUsername(String username) {
        return
                getAllUsers()
                        .stream()
                        .filter((applicationUser -> applicationUser.getUsername().equalsIgnoreCase(username)))
                        .findFirst()
                        .orElseThrow();
    }

    private List<ApplicationUser> getAllUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        "Kshitij",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        "Subash",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMIN_TRAINEE.getGrantedAuthorities(),
                        "Subin",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUsers;
    }
}
