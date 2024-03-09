package com.quiztoast.backend_api.event.publisher;

import com.quiztoast.backend_api.event.PasswordResetEvent;

import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * A class for publishing password reset events.
 */
@Component
public class PasswordResetEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public PasswordResetEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Publishes a password reset event for the given user with the provided reset URL.
     *
     * @param user             the user for whom the password reset event is being published
     * @param passwordResetUrl the URL for resetting the password
     */
    @Async
    public void publishPasswordResetEvent(User user, String passwordResetUrl) {
        PasswordResetEvent passwordResetEvent = new PasswordResetEvent(user, passwordResetUrl);
        eventPublisher.publishEvent(passwordResetEvent);
    }
}
