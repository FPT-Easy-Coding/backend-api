package com.quizztoast.backendAPI.model.payload.response;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SimpleErrorResponse {

    private int statusCode;
    private String message;

}