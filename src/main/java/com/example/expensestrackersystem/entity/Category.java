package com.example.expensestrackersystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String title;

    @Enumerated(EnumType.STRING) // This annotation specifies that the enum should be stored as a STRING in the database
    private CategoryType type;

    private Integer userId;

}
