package com.example.expensestrackersystem.controller;

import com.example.expensestrackersystem.dto.CategoryDTO;
import com.example.expensestrackersystem.entity.Category;
import com.example.expensestrackersystem.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/category")
@RequiredArgsConstructor
@CrossOrigin("*")

public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> postCategory(@Validated @RequestBody CategoryDTO dto){
        Category createdCategory = categoryService.postCategory(dto);
        if(createdCategory != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/getAllCategoriesByUserId/{userId}")
    public ResponseEntity<?> getAllCategoriesByUserId(@PathVariable Integer userId) {
        try {
            List<Category> categories = categoryService.getAllCategoriesByUserId(userId);
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No categories found for user ID: " + userId);
            }
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO dto) {
        try{
            return ResponseEntity.ok(categoryService.updateCategory(id, dto));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category with ID " + id + " deleted successfully");
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/search/{userId}/{title}")
    public ResponseEntity<List<Category>> searchCategories(@PathVariable Integer userId, @PathVariable String title) {
        List<Category> categories = categoryService.searchCategoryByTitle(title, userId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/expenses/{userId}")
    public ResponseEntity<List<Category>> getExpenseCategoriesByUserId(@PathVariable Integer userId) {
        List<Category> categories = categoryService.getExpenseCategoryByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/incomes/{userId}")
    public ResponseEntity<List<Category>> getIncomeCategoriesByUserId(@PathVariable Integer userId) {
        List<Category> categories = categoryService.getIncomeCategoryByUserId(userId);
        return ResponseEntity.ok(categories);
    }

}
