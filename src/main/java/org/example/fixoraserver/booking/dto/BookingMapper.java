package org.example.fixoraserver.booking.dto;

import org.example.fixoraserver.booking.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingResponse toResponse(Booking booking);
    Booking toEntity(BookingRequest bookingRequest);
}
