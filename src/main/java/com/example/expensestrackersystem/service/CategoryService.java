package com.example.expensestrackersystem.service;

import com.example.expensestrackersystem.dto.CategoryDTO;
import com.example.expensestrackersystem.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    Category postCategory(CategoryDTO categoryDTO);

    List<Category> getAllCategories();

    List<Category> getAllCategoriesByUserId(Integer userId);

    Category getCategoryById(Integer id);

    Category updateCategory(Integer id, CategoryDTO categoryDTO);

    void deleteCategory(Integer id);

    List<Category> searchCategoryByTitle(String title, Integer userId);

    List<Category> getExpenseCategoryByUserId(Integer userId);

    List<Category> getIncomeCategoryByUserId(Integer userId);
}
