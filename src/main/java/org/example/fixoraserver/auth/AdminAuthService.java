package org.example.fixoraserver.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class AdminAuthService {
    private final String adminId;
    private final String adminEmail;
    private final String adminPassword;
    private final String adminFullName;

    public AdminAuthService(
            @Value("${fixora.admin.id}") String adminId,
            @Value("${fixora.admin.email}") String adminEmail,
            @Value("${fixora.admin.password}") String adminPassword,
            @Value("${fixora.admin.full-name}") String adminFullName
    ) {
        this.adminId = adminId;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminFullName = adminFullName;
    }

    public boolean isValidAdminLogin(String email, String password) {
        return constantTimeEquals(adminEmail, email) && constantTimeEquals(adminPassword, password);
    }

    public AdminUser adminUser() {
        return new AdminUser(adminId, adminEmail, adminFullName, "ADMIN");
    }

    private boolean constantTimeEquals(String expected, String actual) {
        if (expected == null || actual == null) {
            return false;
        }

        return MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8),
                actual.getBytes(StandardCharsets.UTF_8)
        );
    }
}
