package org.example.fixoraserver.booking.dto;

import org.example.fixoraserver.booking.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "professionalId", expression = "java(booking.getProfessional() != null ? booking.getProfessional().getId().toString() : null)")
    BookingResponse toResponse(Booking booking);

    Booking toEntity(BookingRequest bookingRequest);
}
