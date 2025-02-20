package com.example.expensestrackersystem.dto;

import com.example.expensestrackersystem.entity.CategoryType;
import lombok.Data;

@Data
public class CategoryDTO {

    private Integer id;

    private String title;

    private CategoryType type;

    private Integer userId;

}
