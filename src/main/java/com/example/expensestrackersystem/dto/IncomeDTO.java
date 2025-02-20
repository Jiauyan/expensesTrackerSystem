package com.example.expensestrackersystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeDTO {

    private Integer id;

    private String title;

    private String description;

    private String category;

    private LocalDate date;

    private Double amount;

    private Integer userId;
}
