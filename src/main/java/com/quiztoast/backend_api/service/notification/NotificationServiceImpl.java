package com.quiztoast.backend_api.service.notification;

import com.quiztoast.backend_api.model.entity.user.User;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(String destination, String message) {
        messagingTemplate.convertAndSend(destination, message);
    }

    @Override
    public void sendNotificationToUser(User user, String message) {

        String destination = "/user/" + user.getUserId() + "/queue/notification";
        messagingTemplate.convertAndSend(destination, message);
    }
}
