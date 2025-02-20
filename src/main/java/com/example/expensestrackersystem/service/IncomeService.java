package com.example.expensestrackersystem.service;

import com.example.expensestrackersystem.dto.IncomeDTO;
import com.example.expensestrackersystem.entity.Income;

import java.util.List;

public interface IncomeService {

    Income postIncome(IncomeDTO incomeDTO);

    List<Income> getAllIncomes();

    List<Income> getAllIncomesByUserId(Integer userId);

    Income getIncomeById(Integer id);

    Income updateIncome(Integer id, IncomeDTO incomeDTO);

    void deleteIncome(Integer id);

    List<Income> searchIncomeByTitle(String title, Integer userId);
}
