package org.example.fixoraserver.email

interface EmailService {
    fun sendEmail(emailTemplate: String, toEmail: String)
}