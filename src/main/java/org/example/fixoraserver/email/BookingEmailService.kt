package org.example.fixoraserver.email


import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BookingEmailService {
    @Value("\${resend.api.key}\"")
    lateinit var resendApiKey : String

    fun sendEmail(){

    }

    fun createBookingEmailTemplate() : String {
        return ""
    }

}