package com.quizztoast.backendAPI.event;

import com.quizztoast.backendAPI.event.publisher.PasswordResetEventPublisher;
import com.quizztoast.backendAPI.model.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordResetEvent extends ApplicationEvent {
    private User user;
    private String resetUrl;

    public PasswordResetEvent(User user, String resetUrl) {
        super(user);
        this.user = user;
        this.resetUrl = resetUrl;
    }
}
