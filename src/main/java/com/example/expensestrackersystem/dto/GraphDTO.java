package com.example.expensestrackersystem.dto;

import com.example.expensestrackersystem.entity.Expense;
import com.example.expensestrackersystem.entity.Income;
import lombok.Data;

import java.util.List;

@Data
public class GraphDTO {

    private List<Expense> expenseList;

    private List<Income> incomeList;
}
