package org.example.fixoraserver.auth;

public record LoginRequest(
        String email,
        String username,
        String password
) {
}
