package org.example.fixoraserver.professional.dto;

public record ProfessionalRequest(
        String workerName,
        String workerEmail,
        String phoneNumber,
        String category
) { }
