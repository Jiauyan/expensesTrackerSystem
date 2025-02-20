package com.example.expensestrackersystem.implementation;

import com.example.expensestrackersystem.dto.GraphDTO;
import com.example.expensestrackersystem.dto.StatsDTO;
import com.example.expensestrackersystem.entity.Expense;
import com.example.expensestrackersystem.entity.Income;
import com.example.expensestrackersystem.repository.ExpenseRepository;
import com.example.expensestrackersystem.repository.IncomeRepository;
import com.example.expensestrackersystem.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    @Override
    public GraphDTO getChartData(Integer userId){
        GraphDTO graphDTO = new GraphDTO();
        graphDTO.setExpenseList(expenseRepository.findAllByUserId(userId));
        graphDTO.setIncomeList(incomeRepository.findAllByUserId(userId));

        return graphDTO;
    }

    @Override
    public StatsDTO getStats(Integer userId){
        Double totalIncome = incomeRepository.sumAllAmountsByUserId(userId);
        Double totalExpense = expenseRepository.sumAllAmountsByUserId(userId);

        Optional<Income> latestIncome = incomeRepository.findFirstByUserIdOrderByIdDesc(userId);
        Optional<Expense> latestExpense = expenseRepository.findFirstByUserIdOrderByIdDesc(userId);

        List<Income> incomeList = incomeRepository.findAllByUserId(userId);
        List<Expense> expenseList = expenseRepository.findAllByUserId(userId);

        OptionalDouble minIncome = incomeList.stream().mapToDouble(Income::getAmount).min();
        OptionalDouble maxIncome = incomeList.stream().mapToDouble(Income::getAmount).max();
        OptionalDouble minExpense = expenseList.stream().mapToDouble(Expense::getAmount).min();
        OptionalDouble maxExpense = expenseList.stream().mapToDouble(Expense::getAmount).max();

        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setIncome(totalIncome);
        statsDTO.setExpense(totalExpense);

        latestIncome.ifPresent(statsDTO::setLatestIncome);
        latestExpense.ifPresent(statsDTO::setLatestExpense);

        statsDTO.setBalance(totalIncome - totalExpense);

        statsDTO.setMaxIncome(maxIncome.isPresent() ? maxIncome.getAsDouble() : null);
        statsDTO.setMinIncome(minIncome.isPresent() ? minIncome.getAsDouble() : null);
        statsDTO.setMaxExpense(maxExpense.isPresent() ? maxExpense.getAsDouble() : null);
        statsDTO.setMinExpense(minExpense.isPresent() ? minExpense.getAsDouble() : null);

        return statsDTO;
    }
}
