package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Book;
import com.example.bookrental.entity.Category;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lib/category")
@RequiredArgsConstructor
@SecurityRequirement(name = "bookRental")
@Tag(name = "Category Controller", description = "APIs for managing Category")
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @Operation(summary = "Add Category", description = "Add Category to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category added"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping("/add-category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Category> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return successResponse(categoryService.addCategory(categoryDto), "Category added");
    }

    @Operation(summary = "Update Category", description = "update the available Category detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PutMapping("/update-category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Category> updateCategory(@RequestBody CategoryDto categoryDto) {
        return successResponse(categoryService.updateCategory(categoryDto), "Category Updated");
    }
    @Operation(summary = "Get all Category", description = "Fetch all available Category detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available Category"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/get-all-category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Category>> getAllCategory() {
        return successResponse(categoryService.getAllCategory(), "All available Categories");
    }

    @Operation(summary = "Get Category by id", description = "Fetch available Category detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/get-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Category> getById(@RequestParam Long id){
        return successResponse(categoryService.findById(id),"Category id-:"+id+"details");
    }

    @Operation(summary = "delete Category by id", description = "delete available Category detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found and Deleted"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "402", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping("/delete-category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteCategory(@RequestParam long id) {
        return successResponse(categoryService.deleteCategory(id), "Category" + id + " has been deleted");
    }
}
