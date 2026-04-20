package org.example.fixoraserver.professional;

import lombok.RequiredArgsConstructor;
import org.example.fixoraserver.professional.dto.ProfessionalRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {
    private final ProfessionalService professionalService;

    @PostMapping
    public ResponseEntity<?> addProfessional(ProfessionalRequest professionalRequest){
        professionalService.addProfessional(professionalRequest);
        return ResponseEntity.ok().build();
    }
}
