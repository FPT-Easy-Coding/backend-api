package com.quizztoast.backendAPI.security.auth.event.listener;

import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.security.auth.event.RegistrationCompleteEvent;
import com.quizztoast.backendAPI.service.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserServiceImpl userServiceImpl;
    private final JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userServiceImpl.saveVerificationToken(user, verificationToken);
        String applicationUrl = event.getApplicationUrl() + "/api/v1/auth/register/verifyEmail?token=" + verificationToken;
        try {
            sendVerificationEmail(user, applicationUrl);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Email address {} has been registered. Please click the link below to verify your account: {}", user.getEmail(), applicationUrl);

    }

    public void sendVerificationEmail(User user, String applicationUrl) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String content = "<p>Dear " + user.getLastName() + " " + user.getFirstName() + ",</p>" +
                "Please, follow the link below to complete your registration:" +
                "<a href=\"" + applicationUrl + "\">Click here to activate your account</a>" +
                "<p>Thank you</p> User Registration Portal Service";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("ngothuthuylc@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        javaMailSender.send(message);
    }
}
