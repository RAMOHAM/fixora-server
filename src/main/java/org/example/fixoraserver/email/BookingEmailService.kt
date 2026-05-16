package org.example.fixoraserver.email

import com.resend.services.emails.model.CreateEmailOptions
import org.example.fixoraserver.booking.dto.BookingRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
open class BookingEmailService : EmailService<BookingRequest> {
    private val log = LoggerFactory.getLogger(BookingEmailService::class.java)

    @Value("\${resend.api.key}")
    lateinit var resendApiKey: String

    @Value("\${resend.from.email}")
    lateinit var resendFromEmail: String

    private val resend: com.resend.Resend by lazy {
        com.resend.Resend(resendApiKey)
    }

    @Async("emailTaskExecutor")
    override fun sendEmail(emailTemplate: String, toEmail: String) {
        try {
            val emailParams = CreateEmailOptions.builder()
                .from(resendFromEmail)
                .to(toEmail)
                .subject("Fixora booking update")
                .html(emailTemplate)
                .build()

            resend.emails().send(emailParams)
            log.info("Booking Email Service : Email sent successfully")
        } catch (e: Exception) {
            log.error("Booking Email Service : Error sending email: ${e.message}")
        }
    }

    override fun createEmailTemplate(data: BookingRequest): String {
        return when (data.bookingStatus()?.uppercase() ?: "PENDING") {
            "CONFIRMED" -> createBookingConfirmationTemplate(data)
            "CANCELLED" -> createBookingCancellationTemplate(data)
            else -> createBookingRequestTemplate(data)
        }
    }

    private fun createBookingRequestTemplate(bookingRequest: BookingRequest): String {
        return renderTemplate(
            title = "We received your service request",
            badge = "Request received",
            intro = "Thanks for choosing Fixora. Your booking request has been received and our admin team will review the details shortly.",
            nextStepTitle = "What happens next",
            nextStep = "We will confirm availability and contact you if we need any additional information before assigning the job.",
            bookingRequest = bookingRequest
        )
    }

    private fun createBookingConfirmationTemplate(bookingRequest: BookingRequest): String {
        return renderTemplate(
            title = "Your Fixora booking is confirmed",
            badge = "Booking confirmed",
            intro = "Your service appointment has been confirmed. Please keep the booking details below for your records.",
            nextStepTitle = "Before the appointment",
            nextStep = "Please make sure the service area is accessible at the scheduled time. If anything changes, reply to this email and our team will help.",
            bookingRequest = bookingRequest
        )
    }

    private fun createBookingCancellationTemplate(bookingRequest: BookingRequest): String {
        return renderTemplate(
            title = "Your Fixora booking was cancelled",
            badge = "Booking cancelled",
            intro = "This booking has been cancelled. We have included the original request details below for your records.",
            nextStepTitle = "Need to book again?",
            nextStep = "You can submit a new request at any time and our team will be glad to help with the next available appointment.",
            bookingRequest = bookingRequest
        )
    }

    private fun renderTemplate(
        title: String,
        badge: String,
        intro: String,
        nextStepTitle: String,
        nextStep: String,
        bookingRequest: BookingRequest
    ): String {
        val bookingReference = escapeHtml(bookingRequest.id()?.takeIf { it.isNotBlank() } ?: "Pending")

        return """
        <!DOCTYPE html>
        <html lang="en">
        <body style="margin:0;background:#f4f7fb;color:#172033;font-family:Arial,'Helvetica Neue',Helvetica,sans-serif">
          <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="background:#f4f7fb;padding:32px 12px">
            <tr>
              <td align="center">
                <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="max-width:640px;background:#ffffff;border:1px solid #e5eaf2;border-radius:8px;overflow:hidden">
                  <tr>
                    <td style="background:#172033;color:#ffffff;padding:28px 32px">
                      <div style="font-size:14px;font-weight:700;letter-spacing:0;text-transform:uppercase;color:#9dd7c4">Fixora</div>
                      <h1 style="margin:12px 0 0;font-size:28px;line-height:1.25;font-weight:700">${escapeHtml(title)}</h1>
                    </td>
                  </tr>
                  <tr>
                    <td style="padding:32px">
                      <div style="display:inline-block;background:#eaf7f1;color:#176b55;border-radius:999px;padding:7px 12px;font-size:13px;font-weight:700">${escapeHtml(badge)}</div>
                      <p style="margin:20px 0 0;font-size:16px;line-height:1.65;color:#344054">${escapeHtml(intro)}</p>

                      <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="margin-top:28px;border-collapse:collapse;border:1px solid #e5eaf2;border-radius:8px;overflow:hidden">
                        <tr>
                          <td colspan="2" style="background:#f8fafc;padding:14px 18px;font-size:16px;font-weight:700;color:#172033">Booking details</td>
                        </tr>
                        ${detailRow("Reference", bookingReference)}
                        ${detailRow("Service category", escapeHtml(bookingRequest.category()))}
                        ${detailRow("Date", escapeHtml(bookingRequest.dateOfJob()))}
                        ${detailRow("Preferred window", escapeHtml(bookingRequest.preferredWindow()))}
                        ${detailRow("Address", escapeHtml(bookingRequest.address()))}
                        ${detailRow("Phone", escapeHtml(bookingRequest.phone()))}
                        ${detailRow("Email", escapeHtml(bookingRequest.email()))}
                        ${detailRow("Job description", escapeHtml(bookingRequest.jobDescription()))}
                      </table>

                      <h2 style="margin:30px 0 10px;font-size:18px;line-height:1.35;color:#172033">${escapeHtml(nextStepTitle)}</h2>
                      <p style="margin:0;font-size:15px;line-height:1.65;color:#344054">${escapeHtml(nextStep)}</p>

                      <p style="margin:28px 0 0;font-size:15px;line-height:1.65;color:#344054">Questions or updates? Reply to this email and the Fixora team will take care of it.</p>
                      <p style="margin:24px 0 0;font-size:15px;line-height:1.65;color:#344054">Best regards,<br/><strong>The Fixora Team</strong></p>
                    </td>
                  </tr>
                  <tr>
                    <td style="padding:20px 32px;background:#f8fafc;border-top:1px solid #e5eaf2;color:#667085;font-size:12px;line-height:1.5">
                      Fixora Home Services<br/>
                      This email was sent about your Fixora booking request.
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </body>
        </html>
        """.trimIndent()
    }

    private fun detailRow(label: String, value: String): String {
        return """
        <tr>
          <td style="width:38%;padding:12px 18px;border-top:1px solid #e5eaf2;color:#667085;font-size:14px;vertical-align:top">${escapeHtml(label)}</td>
          <td style="padding:12px 18px;border-top:1px solid #e5eaf2;color:#172033;font-size:14px;line-height:1.5;vertical-align:top">$value</td>
        </tr>
        """.trimIndent()
    }

    private fun escapeHtml(value: String?): String {
        return value
            ?.replace("&", "&amp;")
            ?.replace("<", "&lt;")
            ?.replace(">", "&gt;")
            ?.replace("\"", "&quot;")
            ?.replace("'", "&#39;")
            ?: "Not provided"
    }
}
