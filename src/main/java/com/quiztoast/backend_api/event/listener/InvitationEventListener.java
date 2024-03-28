package com.quiztoast.backend_api.event.listener;

import com.quiztoast.backend_api.event.MemberInviteEvent;
import com.quiztoast.backend_api.model.entity.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvitationEventListener {
    private final JavaMailSender javaMailSender;

    @EventListener
    public void handleInvitationEvent(MemberInviteEvent event) {
        List<User> invitedUsers = event.getInvitedUsers();
        for (User invitedUser : invitedUsers) {
            try {
                sendInvitationEmail(invitedUser);
            } catch (MessagingException e) {
                log.error("Failed to send invitation email to user: {}", invitedUser.getUsername(), e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendInvitationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Invitation to join our Classroom";
        String senderName = "QuizToast";
        String content = "<p>Dear " + user.getUserName() + ",</p>" +
                "You have been invited to join our Classroom. Please, click the link below to accept the invitation:" +
                "<a href=\"https://localhost:5173/classroom/accept-invitation?token=" + 123 +
                "\">Click here to accept the invitation</a>" +
                "<p>Thank you</p> QuizToast";

        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("hieuhvhe176256@fpt.edu.vn", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        javaMailSender.send(message);

        log.info("Invitation email sent to user: {}", user.getUsername());
    }
}
