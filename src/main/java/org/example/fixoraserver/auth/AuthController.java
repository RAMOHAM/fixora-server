package org.example.fixoraserver.auth;

import lombok.NonNull;
import org.example.fixoraserver.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AdminAuthService adminAuthService;
    private final JwtService jwtService;

    public AuthController(AdminAuthService adminAuthService, JwtService jwtService) {
        this.adminAuthService = adminAuthService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<@NonNull LoginResponse> login(@RequestBody LoginRequest request) {
        String loginIdentifier = request.email() != null ? request.email() : request.username();
        if (!adminAuthService.isValidAdminLogin(loginIdentifier, request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        AdminUser user = adminAuthService.adminUser();
        String accessToken = jwtService.createAccessToken(user);
        return ResponseEntity.ok(new LoginResponse(accessToken, null, user));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<@NonNull AdminUser> me() {
        return ResponseEntity.ok(adminAuthService.adminUser());
    }
}
