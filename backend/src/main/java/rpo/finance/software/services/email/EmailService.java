package rpo.finance.software.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationMail(String email, String token) {
        String subject = "Подтвердите ваш аккаунт";
        String verificationUrl = "http://localhost:8080/auth/verify?token=" + token;

        String htmlContent = buildEmail(email, verificationUrl);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Не удалось отправить email", e);
        }
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Arial,sans-serif;font-size:16px;margin:0;padding:20px;color:#333\">\n" +
                "    <table style=\"width:100%;background-color:#f9f9f9;padding:20px;\">\n" +
                "        <tr>\n" +
                "            <td style=\"text-align:center;padding:10px 0;background-color:#0073e6;color:#fff;font-size:20px;font-weight:bold;\">\n" +
                "                Подтвердите вашу электронную почту\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"padding:20px;background-color:#fff;border:1px solid #ddd;border-radius:5px;\">\n" +
                "                <p style=\"font-size:18px;margin-bottom:20px;\">Здравствуйте, " + name + "!</p>\n" +
                "                <p style=\"font-size:16px;line-height:1.5;\">\n" +
                "                    Спасибо за регистрацию! Пожалуйста, нажмите на кнопку ниже, чтобы подтвердить ваш адрес электронной почты.\n" +
                "                </p>\n" +
                "                <p style=\"text-align:center;margin:20px 0;\">\n" +
                "                    <a href=\"" + link + "\" style=\"background-color:#0073e6;color:#fff;padding:10px 20px;text-decoration:none;border-radius:5px;font-size:16px;\">Активировать аккаунт</a>\n" +
                "                </p>\n" +
                "                <p style=\"font-size:14px;color:#888;\">\n" +
                "                    Ссылка действует в течение 20 минут. Если вы не запрашивали этот email, просто проигнорируйте его.\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"text-align:center;font-size:12px;color:#888;padding-top:20px;\">\n" +
                "                © 2024 Ваша компания. Все права защищены.\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</div>";
    }
}
