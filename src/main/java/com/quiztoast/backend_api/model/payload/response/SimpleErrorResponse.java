package com.quiztoast.backend_api.model.payload.response;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SimpleErrorResponse {

    private int statusCode;
    private String message;

}