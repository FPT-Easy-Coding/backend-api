package com.quiztoast.backend_api.event.publisher;

// RegistrationEventPublisher.java

import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.event.RegistrationCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public RegistrationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Publishes a registration event for a user.
     *
     * @param user           the user for whom the registration event is being published
     * @param applicationUrl the application URL associated with the registration event
     */
    @Async
    public void publishRegistrationEvent(User user, String applicationUrl) {
        RegistrationCompleteEvent registrationCompleteEvent = new RegistrationCompleteEvent(user, applicationUrl);
        eventPublisher.publishEvent(registrationCompleteEvent);
    }
}
