package org.example.fixoraserver.auth;

public record AdminUser(
        String id,
        String email,
        String fullName,
        String role
) {
}
