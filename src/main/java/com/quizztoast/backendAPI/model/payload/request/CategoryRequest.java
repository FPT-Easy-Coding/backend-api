package com.quizztoast.backendAPI.model.payload.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @Valid
    @NotNull(message = "categoryName cannot be null")
    @NotBlank(message = "categoryName cannot be Blank")
    private String categoryName;
}
