package com.example.expensestrackersystem.repository;

import com.example.expensestrackersystem.entity.Category;
import com.example.expensestrackersystem.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    void deleteByUserId(Integer userId);

    List<Category> findAllByUserId(Integer userId);

    List<Category> findByTitleContainingIgnoreCaseAndUserId(String title, Integer userId);

    List<Category> findByTypeAndUserId(CategoryType type, Integer userId);

}
