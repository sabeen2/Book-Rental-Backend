package com.example.bookrental.controller;

import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Category;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Lib/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add-Category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Category> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return GenericResponse.<Category>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.addCategory(categoryDto))
                .build();
    }

    @PutMapping("/update-Category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Category> updateCategory(@RequestBody CategoryDto categoryDto) {
        return GenericResponse.<Category>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.updateCategory(categoryDto))
                .build();
    }

    @GetMapping("/get-all-Category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Category>> getAllCategory() {

        return GenericResponse.<List<Category>>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.getAllCategory())
                .build();
    }

    @DeleteMapping("/deleteCategory")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteCategory(@RequestParam long id) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("Category added")
                .data(categoryService.deleteCategory(id))
                .build();
    }
}
