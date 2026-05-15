package org.example.fixoraserver.email
import com.resend.services.emails.model.CreateEmailOptions
import org.example.fixoraserver.booking.dto.BookingRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
open class BookingEmailService : EmailService<BookingRequest> {
    private val log = LoggerFactory.getLogger(BookingEmailService::class.java);

    @Value("\${resend.api.key}")
    lateinit var resendApiKey: String

    @Value("\${resend.from.email}")
    lateinit var resendFromEmail: String

    private val resend: com.resend.Resend by lazy {
        com.resend.Resend(resendApiKey)
    }

    @Async("emailTaskExecutor")
    override fun sendEmail(emailTemplate: String, toEmail: String){
        try{
            val emailParms = CreateEmailOptions.builder().
            from(resendFromEmail).
            to(toEmail).
            subject("Booking Confirmation").
            html(emailTemplate).
            build()

            resend.emails().send(emailParms)
            log.info("Booking Email Service : Email sent successfully")
        }catch(e: Exception){
            log.error("Booking Email Service : Error sending email: ${e.message}")
        }
    }

    override fun createEmailTemplate(data: BookingRequest): String {
        return if(data.bookingStatus() == "PENDING") createBookingRequestTemplate(data)
        else if (data.bookingStatus() == "CONFIRMED") createBookingConfirmationTemplate(data)
        else createBookingCancellationTemplate(data)
    }

    private fun createBookingRequestTemplate(bookingRequest: BookingRequest): String {
        return """
        <!DOCTYPE html>
        <html>
        <body style="background-color:#ffffff;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif">
          <table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
            <tr><td style="font-size:1em;line-height:155%;background-color:#ffffff">
              <table align="left" width="100%" border="0" cellpadding="0" cellspacing="0"
                style="max-width:600px;color:#000000;background-color:#ffffff">
                <tr><td>
                  <h1 style="font-size:2.25em;font-weight:600">Service Request Confirmation</h1>
                  <p>Hi Customer,</p>
                  <p>Thank you for reaching out to us. We have received your booking request and will be in touch shortly to confirm your appointment.</p>

                  <h2 style="font-size:1.8em;font-weight:600">Your Booking Details</h2>
                  <p>
                    <strong>Service Category:</strong> ${bookingRequest.category()}<br/>
                    <strong>Preferred Time Window:</strong> ${bookingRequest.preferredWindow()}<br/>
                    <strong>Date of Job:</strong> ${bookingRequest.dateOfJob()}<br/>
                    <strong>Job Description:</strong> ${bookingRequest.jobDescription()}<br/>
                  </p>

                  <h3 style="font-size:1.4em;font-weight:600">What Happens Next?</h3>
                  <p>Our team will arrive on this time, to come assess your requested service</p>

                  <p>If you have any questions, feel free to reply directly to this email. We look forward to helping you.</p>

                  <p>Best regards,<br/>The Fixora Team</p>

                  <table width="100%" style="font-size:0.8em">
                    <tr><td>
                      <p>Fixora • 123 Main Street, Suite 100, City, ST 00000<br/>
                      © 2026 Company Name</p>
                    </td></tr>
                  </table>
                </td></tr>
              </table>
            </td></tr>
          </table>
        </body>
        </html>
        """.trimIndent()
    }

    private fun createBookingConfirmationTemplate(bookingRequest: BookingRequest): String {
        return """
        <!DOCTYPE html>
        <html>
        <body style="background-color:#ffffff;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif">
          <table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
            <tr><td style="font-size:1em;line-height:155%;background-color:#ffffff">
              <table align="left" width="100%" border="0" cellpadding="0" cellspacing="0"
                style="max-width:600px;color:#000000;background-color:#ffffff">
                <tr><td>
                  <h1 style="font-size:2.25em;font-weight:600">Service Request Confirmation</h1>
                  <p>Hi Customer,</p>
                  <p>Thank you for reaching out to us. We have Confirmed your Booking will take place.</p>

                  <h2 style="font-size:1.8em;font-weight:600">Your Booking Details</h2>
                  <p>
                    <strong>Service Category:</strong> ${bookingRequest.category()}<br/>
                    <strong>Preferred Time Window:</strong> ${bookingRequest.preferredWindow()}<br/>
                    <strong>Date of Job:</strong> ${bookingRequest.dateOfJob()}<br/>
                    <strong>Job Description:</strong> ${bookingRequest.jobDescription()}<br/>
                  </p>

                  <h3 style="font-size:1.4em;font-weight:600">What Happens Next?</h3>
                  <p>Our team will review your request and contact you by email to confirm your appointment and provide a quote.</p>

                  <p>If you have any questions, feel free to reply directly to this email. We look forward to helping you.</p>

                  <p>Best regards,<br/>The Fixora Team</p>

                  <table width="100%" style="font-size:0.8em">
                    <tr><td>
                      <p>Fixora • 123 Main Street, Suite 100, City, ST 00000<br/>
                      © 2026 Company Name</p>
                    </td></tr>
                  </table>
                </td></tr>
              </table>
            </td></tr>
          </table>
        </body>
        </html>
        """.trimIndent()
    }


    private fun createBookingCancellationTemplate(bookingRequest: BookingRequest): String {
        return """
        <!DOCTYPE html>
        <html>
        <body style="background-color:#ffffff;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif">
          <table border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
            <tr><td style="font-size:1em;line-height:155%;background-color:#ffffff">
              <table align="left" width="100%" border="0" cellpadding="0" cellspacing="0"
                style="max-width:600px;color:#000000;background-color:#ffffff">
                <tr><td>
                  <h1 style="font-size:2.25em;font-weight:600">Service Request Confirmation</h1>
                  <p>Hi Customer,</p>
                  <p>Thank you for reaching out to us. Your Booking has been Cancelled.</p>

                  <h2 style="font-size:1.8em;font-weight:600">Your Booking Details</h2>
                  <p>
                    <strong>Service Category:</strong> ${bookingRequest.category()}<br/>
                    <strong>Preferred Time Window:</strong> ${bookingRequest.preferredWindow()}<br/>
                    <strong>Date of Job:</strong> ${bookingRequest.dateOfJob()}<br/>
                    <strong>Job Description:</strong> ${bookingRequest.jobDescription()}<br/>
                  </p>

                  <p>We hope you will contact us again with another query soon</p>

                  <p>If you have any questions, feel free to reply directly to this email. We look forward to helping you.</p>

                  <p>Best regards,<br/>The Fixora Team</p>

                  <table width="100%" style="font-size:0.8em">
                    <tr><td>
                      <p>Fixora • 123 Main Street, Suite 100, City, ST 00000<br/>
                      © 2026 Company Name</p>
                    </td></tr>
                  </table>
                </td></tr>
              </table>
            </td></tr>
          </table>
        </body>
        </html>
        """.trimIndent()
    }

}