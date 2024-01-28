package com.quizztoast.backendAPI.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @Valid
    @NotNull(message = "categoryId cannot be null")
    private Integer categoryId;

    @NotNull(message = "categoryName cannot be null")
    @NotEmpty(message = "categoryName cannot be blank")
    private String categoryName;
}
