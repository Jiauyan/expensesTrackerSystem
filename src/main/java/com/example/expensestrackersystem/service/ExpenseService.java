package com.example.expensestrackersystem.service;

import com.example.expensestrackersystem.dto.ExpenseDTO;
import com.example.expensestrackersystem.entity.Expense;

import java.util.List;

public interface ExpenseService {

    Expense postExpense(ExpenseDTO expenseDTO);

    List<Expense> getAllExpenses();

    List<Expense> getAllExpensesByUserId(Integer userId);

    Expense getExpenseById(Integer id);

    Expense updateExpense(Integer id, ExpenseDTO expenseDTO);

    void deleteExpense(Integer id);

    List<Expense> searchExpenseByTitle(String title, Integer userId);
}
