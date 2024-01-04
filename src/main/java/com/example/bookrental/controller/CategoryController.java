package com.example.bookrental.controller;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Category;
import com.example.bookrental.generic_response.GenericResponse;
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
    public GenericResponse<Category> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return GenericResponse.<Category>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.addCategory(categoryDto))
                .build();
    }

    @PutMapping("/update-Category")
    public GenericResponse<Category> updateCategory(@RequestBody CategoryDto categoryDto) {
        return GenericResponse.<Category>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.updateCategory(categoryDto))
                .build();
    }

    @GetMapping("/get-all-Category")
    public GenericResponse<List<Category>> getAllCategory() {

        return GenericResponse.<List<Category>>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.getAllCategory())
                .build();
    }

    @DeleteMapping("/deleteCategory")
    public GenericResponse<String> deleteCategory(@RequestParam long id) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.deleteCategory(id))
                .build();
    }
}
