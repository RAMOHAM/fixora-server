package org.example.fixoraserver.booking.dto;

public record BookingResponse (
        String jobDescription,
        String email,
        String address,
        String phone,
        String dateOfJob,
        String preferredWindow,
        String category
){}
