package com.example.expensestrackersystem.repository;

import com.example.expensestrackersystem.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    void deleteByUserId(Integer userId);

    List<Expense> findAllByUserId(Integer userId);

    List<Expense> findByUserIdAndDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId")
    Double sumAllAmountsByUserId(Integer userId);

    Optional<Expense> findFirstByUserIdOrderByIdDesc(Integer userId);

    List<Expense> findByTitleContainingIgnoreCaseAndUserId(String title, Integer userId);

}
