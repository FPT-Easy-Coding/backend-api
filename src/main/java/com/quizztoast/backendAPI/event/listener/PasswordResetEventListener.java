package com.quizztoast.backendAPI.event.listener;

import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.event.PasswordResetEvent;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordResetEventListener implements ApplicationListener<PasswordResetEvent> {
    private final JavaMailSender javaMailSender;

    /**
     * Handles the PasswordResetEvent by sending a reset password email to the user.
     *
     * @param event the PasswordResetEvent triggering the function
     */
    @Override
    public void onApplicationEvent(PasswordResetEvent event) {
        User user = event.getUser();
        String resetUrl = event.getResetUrl(); // Use the reset URL from the event

        try {
            sendResetPasswordEmail(user, resetUrl);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Reset password email sent to {}", user.getEmail());
    }

    /**
     * Sends a reset password email to the user.
     *
     * @param user     the user to whom the email will be sent
     * @param resetUrl the URL for resetting the password
     * @throws MessagingException           if an error occurs during message sending
     * @throws UnsupportedEncodingException if an unsupported encoding is encountered
     */
    public void sendResetPasswordEmail(User user, String resetUrl) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset";
        String senderName = "User Registration Portal Service";
        String content = "<p>Dear " + user.getLastName() + " " + user.getFirstName() + ",</p>" +
                "Please, follow the link below to reset your password:" +
                "<a href=\"" + resetUrl + "\">Click here to reset your password</a>" +
                "<p>Thank you</p> User Registration Portal Service";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("hieuhvhe176256@fpt.edu.vn", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        javaMailSender.send(message);
    }
}
