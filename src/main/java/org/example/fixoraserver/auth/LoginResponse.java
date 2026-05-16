package org.example.fixoraserver.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        AdminUser user
) {
}
