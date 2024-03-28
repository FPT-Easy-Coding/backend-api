package com.quiztoast.backend_api.model.payload.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class InviteMemberRequest {
    private int classId;
    private List<String> inviteMails;
}
