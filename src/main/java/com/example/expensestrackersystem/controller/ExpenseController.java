package com.example.expensestrackersystem.controller;

import com.example.expensestrackersystem.dto.ExpenseDTO;
import com.example.expensestrackersystem.entity.Expense;
import com.example.expensestrackersystem.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/expense")
@RequiredArgsConstructor
@CrossOrigin("*")

public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<?> postExpense(@RequestBody ExpenseDTO dto){
        Expense createdExpense = expenseService.postExpense(dto);
        if(createdExpense != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/getAllExpenses")
    public ResponseEntity<?> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/getAllExpensesByUserId/{userId}")
    public ResponseEntity<?> getAllExpensesByUserId(@PathVariable Integer userId) {
        try {
            List<Expense> expenses = expenseService.getAllExpensesByUserId(userId);
            if (expenses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No expenses found for user ID: " + userId);
            }
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getExpenseById/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(expenseService.getExpenseById(id));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("/updateExpense/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Integer id, @RequestBody ExpenseDTO dto) {
        try{
            return ResponseEntity.ok(expenseService.updateExpense(id, dto));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @DeleteMapping("/deleteExpense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Integer id) {
        try{
            expenseService.deleteExpense(id);
            return ResponseEntity.ok("Expense with ID " + id + " deleted successfully");
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/search/{userId}/{title}")
    public ResponseEntity<List<Expense>> searchExpenses(@PathVariable Integer userId, @PathVariable String title) {
        List<Expense> expenses = expenseService.searchExpenseByTitle(title, userId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
}
