package org.example.fixoraserver.email

interface EmailService<T> {
    /**
     * @param emailTemplate - the template to use for the email
     * @param toEmail - the email to send the email to
     *
     * function uses the resend api to send the email to the specified email
     */
    fun sendEmail(emailTemplate: String, toEmail: String)

    /**
     * @param data - the data needed to create the email template such as Bookings
     */
    fun createEmailTemplate(data: T) : String
}