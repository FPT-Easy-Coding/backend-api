package com.quizztoast.backendAPI.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private int categoryId;
    private String categoryName;
}
