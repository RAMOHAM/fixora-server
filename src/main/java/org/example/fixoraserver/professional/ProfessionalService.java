package org.example.fixoraserver.professional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fixoraserver.professional.dto.ProfessionalMapper;
import org.example.fixoraserver.professional.dto.ProfessionalRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final ProfessionalMapper professionalMapper;

    public void addProfessional(ProfessionalRequest professional) {
        professionalRepository.save(professionalMapper.toEntity(professional));
    }

    public Professional getProfessionalById(String id) {
        return professionalRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public void deleteProfessionalById(String id) {
        try {
            Professional professional = professionalRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new RuntimeException("Professional not found"));


            log.info("Found professional, proceeding to delete: {}", id);
            professionalRepository.delete(professional);
            log.info("Delete completed successfully for id: {}", id);

        } catch (Exception e) {
            log.error("Error during deletion of professional {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public List<ProfessionalRequest> getAllProfessionals() {
        return professionalRepository.findAll().stream().map(professionalMapper::toDTO).toList();
    }
}
