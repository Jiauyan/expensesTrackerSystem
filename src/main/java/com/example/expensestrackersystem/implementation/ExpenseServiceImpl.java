package com.example.expensestrackersystem.implementation;
import com.example.expensestrackersystem.dto.ExpenseDTO;
import com.example.expensestrackersystem.entity.Expense;
import com.example.expensestrackersystem.repository.ExpenseRepository;
import com.example.expensestrackersystem.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense postExpense(ExpenseDTO expenseDTO) {
        return saveOrUpdateExpense(new Expense(), expenseDTO);
    }

    private Expense saveOrUpdateExpense(Expense expense, ExpenseDTO expenseDTO) {
        expense.setTitle(expenseDTO.getTitle());
        expense.setDate(expenseDTO.getDate());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setUserId(expenseDTO.getUserId());
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Expense> getAllExpensesByUserId(Integer userId) {
        return expenseRepository.findAllByUserId(userId).stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .collect(Collectors.toList());
    }

    public Expense getExpenseById(Integer id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            return optionalExpense.get();
        } else {
            throw new EntityNotFoundException("Expense is not present with id" + id);
        }
    }

    public Expense updateExpense(Integer id, ExpenseDTO expenseDTO){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            return saveOrUpdateExpense(optionalExpense.get(), expenseDTO);
        } else {
            throw new EntityNotFoundException("Expense is not present with id" + id);
        }
    }

    public void deleteExpense(Integer id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            expenseRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Expense is not present with id" + id);
        }
    }

    public List<Expense> searchExpenseByTitle(String title, Integer userId) {
        List<Expense> expenses = expenseRepository.findByTitleContainingIgnoreCaseAndUserId(title, userId);
        if (expenses.isEmpty()) {
            throw new EntityNotFoundException("No expenses found with title containing: " + title + "for user ID:" + userId);
        }
        return expenses;
    }

}
