package org.example.fixoraserver.professional;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<@NonNull Professional,@NonNull Long> {
}
