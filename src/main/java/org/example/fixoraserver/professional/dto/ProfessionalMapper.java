package org.example.fixoraserver.professional.dto;

import org.example.fixoraserver.professional.Professional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessionalMapper {
    Professional toEntity(ProfessionalRequest professionalRequest);
}
