package com.quiztoast.backend_api.service.notification;

import com.quiztoast.backend_api.model.entity.user.User;

public interface NotificationService {
    public void sendNotification(String destination, String message);

    void sendNotificationToUser(User recipient, String message);
}
