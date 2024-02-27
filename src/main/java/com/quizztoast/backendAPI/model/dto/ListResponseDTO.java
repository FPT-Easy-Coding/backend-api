package com.quizztoast.backendAPI.model.dto;

import com.quizztoast.backendAPI.model.payload.response.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ListResponseDTO {
    private MessageResponse messageResponse;
    private List<?> entityResponses;

    // Constructor, getters, and setters
}