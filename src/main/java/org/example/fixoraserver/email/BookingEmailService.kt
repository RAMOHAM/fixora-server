package org.example.fixoraserver.email
import com.resend.services.emails.model.CreateEmailOptions
import org.example.fixoraserver.booking.Booking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BookingEmailService : EmailService<Booking> {

    @Value("\${resend.api.key}")
    lateinit var resendApiKey: String

    @Value("\${resend.from.email}")
    lateinit var resendFromEmail: String

    private val resend: com.resend.Resend by lazy {
        com.resend.Resend(resendApiKey)
    }

    override fun sendEmail(emailTemplate: String, toEmail: String){
        val emailParms = CreateEmailOptions.builder().
            from(resendFromEmail).
            to(toEmail).
            subject("Booking Confirmation").
            html(emailTemplate).
            build()

        resend.emails().send(emailParms)
    }

    override fun createEmailTemplate(data: Booking): String {
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
                    <strong>Service Category:</strong> ${data.getCategory()}<br/>
                    <strong>Preferred Time Window:</strong> ${data.getPreferredWindow()}<br/>
                    <strong>Date of Job:</strong> ${data.getDateOfJob()}<br/>
                    <strong>Job Description:</strong> ${data.getJobDescription()}<br/>
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

}