package com.quizztoast.backendAPI.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailForgottenResponse {
    private boolean hasEmail;
}
