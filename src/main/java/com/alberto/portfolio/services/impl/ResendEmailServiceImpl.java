package com.alberto.portfolio.services.impl;

import com.alberto.portfolio.models.ContactaConmigo;
import com.alberto.portfolio.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class ResendEmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(ResendEmailServiceImpl.class);

    private final WebClient webClient;

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Value("${resend.from.email:onboarding@resend.dev}")
    private String fromEmail;

    @Value("${resend.to.email:alberto.cabello95@gmail.com}")
    private String toEmail;

    public ResendEmailServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.resend.com")
                .build();
    }

    @Override
    public void sendContactNotification(ContactaConmigo message) {
        try {
            Map<String, Object> emailRequest = Map.of(
                    "from", fromEmail,
                    "to", new String[]{toEmail},
                    "subject", "Nuevo mensaje de contacto: " + message.getAsunto(),
                    "html", buildEmailHtml(message)
            );

            logger.info("Sending email via Resend...");

            webClient.post()
                    .uri("/emails")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resendApiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(emailRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> logger.info("Email sent successfully: {}", response))
                    .doOnError(error -> logger.error("Failed to send email", error))
                    .onErrorResume(error -> {
                        logger.warn("Email sending failed, but continuing: {}", error.getMessage());
                        return Mono.empty();
                    })
                    .subscribe();

        } catch (Exception e) {
            logger.error("Error sending email notification", e);
        }
    }

    private String buildEmailHtml(ContactaConmigo message) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateStr = message.getCreatedAt() != null ? message.getCreatedAt().format(fmt) : "";
        String safeMsg = message.getMensaje()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");

        return String.format("""
            <div style=\"margin:0;padding:24px;background:#f3f4f6;font-family:system-ui,-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif;\">
              <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"100%%\" style=\"max-width:640px;margin:0 auto;\">
                <tr>
                  <td style=\"padding:0;\">
                    <table role=\"presentation\" width=\"100%%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-radius:18px;background:#ffffff;overflow:hidden;border:1px solid rgba(148,163,184,0.4);box-shadow:0 20px 50px rgba(148,163,184,0.45);\">
                      <tr>
                        <td style=\"padding:18px 22px 12px 22px;border-bottom:1px solid rgba(229,231,235,0.9);background:linear-gradient(135deg,#6366f1,#a855f7,#ec4899);color:#f9fafb;\">
                          <div style=\"font-size:11px;letter-spacing:.18em;text-transform:uppercase;opacity:.9;margin-bottom:4px;\">Nuevo mensaje</div>
                          <h1 style=\"margin:0;font-size:19px;line-height:1.4;\">Has recibido un mensaje de contacto desde tu portfolio</h1>
                        </td>
                      </tr>

                      <tr>
                        <td style=\"padding:18px 22px 10px 22px;\">
                          <table role=\"presentation\" width=\"100%%\" cellspacing=\"0\" cellpadding=\"0\">
                            <tr>
                              <td style=\"padding:10px 12px;border-radius:12px;background:#f9fafb;border:1px solid rgba(148,163,184,0.7);\">
                                <div style=\"font-size:11px;color:#6b7280;letter-spacing:.14em;text-transform:uppercase;margin-bottom:3px;\">De</div>
                                <div style=\"font-size:14px;font-weight:600;color:#111827;\">%s</div>
                              </td>
                              <td style=\"width:14px;\"></td>
                              <td style=\"padding:10px 12px;border-radius:12px;background:#f9fafb;border:1px solid rgba(148,163,184,0.7);\">
                                <div style=\"font-size:11px;color:#6b7280;letter-spacing:.14em;text-transform:uppercase;margin-bottom:3px;\">Fecha</div>
                                <div style=\"font-size:14px;font-weight:500;color:#111827;\">%s</div>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <tr>
                        <td style=\"padding:8px 22px 8px 22px;\">
                          <div style=\"padding:12px 14px;border-radius:12px;background:linear-gradient(135deg,rgba(129,140,248,0.08),rgba(236,72,153,0.03));border:1px solid rgba(129,140,248,0.6);\">
                            <div style=\"font-size:11px;color:#4b5563;letter-spacing:.16em;text-transform:uppercase;margin-bottom:4px;\">Asunto</div>
                            <div style=\"font-size:15px;font-weight:600;color:#111827;\">%s</div>
                          </div>
                        </td>
                      </tr>

                      <tr>
                        <td style=\"padding:8px 22px 20px 22px;\">
                          <div style=\"border-radius:12px;background:#f9fafb;border:1px solid rgba(209,213,219,0.9);padding:14px 16px;\">
                            <div style=\"font-size:11px;color:#6b7280;letter-spacing:.16em;text-transform:uppercase;margin-bottom:6px;\">Mensaje</div>
                            <div style=\"font-size:14px;line-height:1.7;color:#111827;\">%s</div>
                          </div>
                        </td>
                      </tr>

                      <tr>
                        <td style=\"padding:0 22px 16px 22px;\">
                          <div style=\"display:flex;align-items:center;gap:8px;font-size:11px;color:#6b7280;\">
                            <span style=\"display:inline-block;width:6px;height:6px;border-radius:999px;background:linear-gradient(135deg,#6366f1,#a855f7);box-shadow:0 0 0 3px rgba(129,140,248,0.35);\"></span>
                            Enviado automáticamente desde tu portfolio React.
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </div>
            """,
                message.getEmail(),
                dateStr,
                message.getAsunto(),
                safeMsg
        );
    }
}
