package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
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
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @PostMapping("/add-Category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Category> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return successResponse(categoryService.addCategory(categoryDto), "Category added");
    }

    @PutMapping("/update-Category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Category> updateCategory(@RequestBody CategoryDto categoryDto) {
        return successResponse(categoryService.updateCategory(categoryDto), "Category Updated");
    }

    @GetMapping("/get-all-Category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Category>> getAllCategory() {
        return successResponse(categoryService.getAllCategory(), "All available Categories");
    }

    @DeleteMapping("/deleteCategory")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteCategory(@RequestParam long id) {
        return successResponse(categoryService.deleteCategory(id), "Category" + id + " has been deleted");
    }
}
