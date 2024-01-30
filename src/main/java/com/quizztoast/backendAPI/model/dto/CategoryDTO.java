package com.quizztoast.backendAPI.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;
}
