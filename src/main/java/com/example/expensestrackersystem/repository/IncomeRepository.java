package com.example.expensestrackersystem.repository;

import com.example.expensestrackersystem.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    void deleteByUserId(Integer userId);

    List<Income> findAllByUserId(Integer userId);

    List<Income> findByUserIdAndDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.userId = :userId")
    Double sumAllAmountsByUserId(Integer userId);

    Optional<Income> findFirstByUserIdOrderByIdDesc(Integer userId);

    List<Income> findByTitleContainingIgnoreCaseAndUserId(String title, Integer userId);

}
