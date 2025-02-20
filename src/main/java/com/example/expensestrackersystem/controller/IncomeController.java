package com.example.expensestrackersystem.controller;

import com.example.expensestrackersystem.dto.IncomeDTO;
import com.example.expensestrackersystem.entity.Income;
import com.example.expensestrackersystem.service.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
@CrossOrigin("*")

public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping("/add")
    public ResponseEntity<?> postIncome(@RequestBody IncomeDTO dto){
        Income createdIncome = incomeService.postIncome(dto);
        if(createdIncome != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/getAllIncomes")
    public ResponseEntity<?> getAllIncomes() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @GetMapping("/getAllIncomesByUserId/{userId}")
    public ResponseEntity<?> getAllIncomesByUserId(@PathVariable Integer userId) {
        try {
            List<Income> incomes = incomeService.getAllIncomesByUserId(userId);
            if (incomes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No incomes found for user ID: " + userId);
            }
            return ResponseEntity.ok(incomes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getIncomeById/{id}")
    public ResponseEntity<?> getIncomeById(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(incomeService.getIncomeById(id));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("/updateIncome/{id}")
    public ResponseEntity<?> updateIncome(@PathVariable Integer id, @RequestBody IncomeDTO dto) {
        try{
            return ResponseEntity.ok(incomeService.updateIncome(id, dto));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @DeleteMapping("/deleteIncome/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Integer id) {
        try{
            incomeService.deleteIncome(id);
            return ResponseEntity.ok("Income with ID " + id + " deleted successfully");
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/search/{userId}/{title}")
    public ResponseEntity<List<Income>> searchIncomes(@PathVariable Integer userId,@PathVariable String title) {
        List<Income> incomes = incomeService.searchIncomeByTitle(title,userId);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }
}
