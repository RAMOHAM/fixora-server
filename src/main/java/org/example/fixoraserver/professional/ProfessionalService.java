package org.example.fixoraserver.professional;

import lombok.RequiredArgsConstructor;
import org.example.fixoraserver.professional.dto.ProfessionalMapper;
import org.example.fixoraserver.professional.dto.ProfessionalRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final ProfessionalMapper professionalMapper;

    public void addProfessional(ProfessionalRequest professional) {
        professionalRepository.save(professionalMapper.toEntity(professional));
    }

    public List<ProfessionalRequest> getAllProfessionals() {
        return professionalRepository.findAll().stream().map(professionalMapper::toDTO).toList();
    }
}
