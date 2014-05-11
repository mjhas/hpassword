package eu.haslgruebler.hpassword.data.facade;

import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.MessageSource;

import eu.haslgruebler.hpassword.api.exceptions.ErrorSendingEmail;

public class EmailService {
    private Session session;
    private MessageSource messageSource;
    private InternetAddress from;

    public EmailService(String host, String smtpLocalhost, String from, MessageSource messageSource) {
        try {
            this.from = new InternetAddress(from);
        } catch (AddressException e) {
            throw new IllegalArgumentException(e);
        }
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.localhost", smtpLocalhost);
        session = Session.getDefaultInstance(properties);
        this.messageSource = messageSource;
    }

    public void sendEmail(String to, String token, Locale locale) throws ErrorSendingEmail {
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(messageSource.getMessage("password.resetlink.mail.subject", null, "Reset Password Request", locale));
            message.setText(messageSource.getMessage("password.resetlink.mail.text", new Object[] { token }, "Reset Token: " + token, locale));

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            throw new ErrorSendingEmail("error sending email to " + to);
        }
    }
}
