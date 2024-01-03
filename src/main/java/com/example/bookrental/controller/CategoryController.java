package com.example.bookrental.controller;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Category;
import com.example.bookrental.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add-Category")
    public Category addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PutMapping("/update-Category")
    public Category updateCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @GetMapping("/get-all-Category")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @DeleteMapping("/deleteCategory")
    public String deleteCategory(@RequestParam long id) {
        return categoryService.deleteCategory(id);
    }
}
