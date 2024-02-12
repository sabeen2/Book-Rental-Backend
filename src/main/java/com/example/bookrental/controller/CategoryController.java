package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.CategoryDto;
import com.example.bookrental.entity.Category;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public GenericResponse<String> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
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
    public GenericResponse<String> updateCategory(@RequestBody CategoryDto categoryDto) {
        return successResponse(categoryService.updateCategory(categoryDto), "Category Updated");
    }
    @Operation(summary = "Get all category history ", description = "Fetch all available deleated Category detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available Category"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
        @GetMapping("/find-deleted")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<CategoryDto>> getAllCategory() {
        return successResponse(categoryService.getDeleted(), "All available Categories");
    }

    @Operation(summary = "Get all Category", description = "Fetch all available Category detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available Category"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    //    @GetMapping("/find-all-category")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
//    public GenericResponse<List<Category>> getAllCategory() {
//        return successResponse(categoryService.getAllCategory(), "All available Categories");
//    }

    @GetMapping("/get-all-category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<CategoryDto>> findAllCategory() {
        return successResponse(categoryService.findAllCategory(), "All available Categories");
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
    public GenericResponse<CategoryDto> getById(@RequestParam Long id){
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
        return successResponse(categoryService.deleteCategory(id), "Category-: " + id + " has been deleted");
    }

    @Operation(summary = "download author", description = "download available author detail based on excel sheet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/download-category")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        return successResponse(categoryService.getExcel(response),"excelSheet downloaded");
    }
    @Operation(summary = "Upload author details", description = "upload author detail based of excel sheet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author uploaded"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping(value = "/export-to-db-category" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> excelToDb(@ModelAttribute MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        return successResponse(categoryService.excelToDb(file),"data exported");
    }
}
