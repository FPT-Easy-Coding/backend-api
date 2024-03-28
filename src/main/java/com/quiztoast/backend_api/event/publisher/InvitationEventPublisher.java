package com.quiztoast.backend_api.event.publisher;

import com.quiztoast.backend_api.event.MemberInviteEvent;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvitationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public InvitationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Publishes an invitation event for a list of users.
     *
     * @param invitedUsers the list of users to whom the invitation is sent
     */
    @Async
    public void publishInvitationEvent(List<User> invitedUsers) {
        MemberInviteEvent memberInviteEvent = new MemberInviteEvent(invitedUsers);
        eventPublisher.publishEvent(memberInviteEvent);
    }
}
