package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.AuthorService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lib/authors")
@SecurityRequirement(name = "bookRental")
@Tag(name = "Author Controller", description = "APIs for managing Authors")
public class AuthorController extends BaseController {
    private final AuthorService authorService;

    @Operation(summary = "Add authors", description = "Add authors to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author added"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping("/add-author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> addAuthor(@RequestBody @Valid AuthorDto authorDto) {
        return successResponse(authorService.addAuthor(authorDto), "Author added");
    }

    @Operation(summary = "Update authors", description = "update the available authors detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PutMapping("/update-author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> updateAuthor(@RequestBody AuthorDto authorDto) {
        return successResponse(authorService.updateAuthor(authorDto), "Author Updated");
    }
    @Operation(summary = "Get all authors", description = "Fetch all available authors detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available authors"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/all-authors")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Author>> getAllAuthor() {
        return successResponse(authorService.getAllAuthor(), "All Available Authors");

    }

    @Operation(summary = "Get author by id", description = "Fetch available author detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/find-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> findAuthorById(@RequestParam Long id) {
        return successResponse(authorService.findById(id), "User id-:" + id);
    }

    @Operation(summary = "delete author by id", description = "delete available author detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found and Deleted"),
            @ApiResponse(responseCode = "402", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping("/delete-author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteAuthor(@RequestParam Long id) {
        return successResponse(authorService.deleteAuthor(id),"User id:-"+ id +" deleted");
    }

    @Operation(summary = "download author", description = "download available author detail based on excel sheet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found and Deleted"),
            @ApiResponse(responseCode = "402", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/download-author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        return successResponse(authorService.getExcel(response),"excelSheet downloaded");
    }
}
