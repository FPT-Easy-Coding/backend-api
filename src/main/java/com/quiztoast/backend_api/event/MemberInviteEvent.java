package com.quiztoast.backend_api.event;

import com.quiztoast.backend_api.model.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class MemberInviteEvent extends ApplicationEvent {
    private final List<User> invitedUsers;
    private List<String> inviteUrls;
    public MemberInviteEvent( List<User> invitedUsers, List<String> inviteUrls) {
        super(invitedUsers);
        this.invitedUsers = invitedUsers;
        this.inviteUrls = inviteUrls;
    }
}
