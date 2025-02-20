package com.example.expensestrackersystem.implementation;

import com.example.expensestrackersystem.dto.CategoryDTO;
import com.example.expensestrackersystem.entity.Category;
import com.example.expensestrackersystem.entity.CategoryType;
import com.example.expensestrackersystem.repository.CategoryRepository;
import com.example.expensestrackersystem.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category postCategory(CategoryDTO categoryDTO) {
        return saveOrUpdateCategory(new Category(), categoryDTO);
    }

    private Category saveOrUpdateCategory(Category category, CategoryDTO categoryDTO) {
        category.setTitle(categoryDTO.getTitle());
        category.setType(categoryDTO.getType());
        category.setUserId(categoryDTO.getUserId());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    public List<Category> getAllCategoriesByUserId(Integer userId) {
        return categoryRepository.findAllByUserId(userId);
    }

    public Category getCategoryById(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            return optionalCategory.get();
        } else {
            throw new EntityNotFoundException("Category is not present with id" + id);
        }
    }

    public Category updateCategory(Integer id, CategoryDTO categoryDTO){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            return saveOrUpdateCategory(optionalCategory.get(), categoryDTO);
        } else {
            throw new EntityNotFoundException("Category is not present with id" + id);
        }
    }

    public void deleteCategory(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            categoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Category is not present with id" + id);
        }
    }

    public List<Category> searchCategoryByTitle(String title, Integer userId) {
        List<Category> categories = categoryRepository.findByTitleContainingIgnoreCaseAndUserId(title, userId);
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("No categories found with title containing: " + title);
        }
        return categories;
    }

    public List<Category> getExpenseCategoryByUserId(Integer userId) {
        return categoryRepository.findByTypeAndUserId(CategoryType.Expense, userId);
    }

    public List<Category> getIncomeCategoryByUserId(Integer userId) {
        return categoryRepository.findByTypeAndUserId(CategoryType.Income, userId);
    }
}
