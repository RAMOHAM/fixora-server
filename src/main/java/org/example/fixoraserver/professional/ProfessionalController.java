package org.example.fixoraserver.professional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.fixoraserver.professional.dto.ProfessionalRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {
    private final ProfessionalService professionalService;

    @PostMapping
    public ResponseEntity<?> addProfessional(@RequestBody ProfessionalRequest professionalRequest){
        professionalService.addProfessional(professionalRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<@NonNull List<ProfessionalRequest>> getProfessionals(){
        return ResponseEntity.status(HttpStatus.OK).body(professionalService.getAllProfessionals());
    }
}
