package com.example.Spring.Security.Tutorial.auth;

public interface ApplicationUserRepository {
    ApplicationUser findByUsername(String username);
}
