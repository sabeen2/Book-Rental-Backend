package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Lib/authors")
public class AuthorController extends BaseController {
    private final AuthorService authorService;

    @PostMapping("/add-Author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> addAuthor(@RequestBody @Valid AuthorDto authorDto) {
        return successResponse(authorService.addAuthor(authorDto), "Author added");
    }

    @PutMapping("/update-Author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> updateAuthor(@RequestBody AuthorDto authorDto) {
        return successResponse(authorService.updateAuthor(authorDto), "Author Updated");
    }

    @GetMapping("/all-Authors")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Author>> getAllAuthor() {
        return successResponse(authorService.getAllAuthor(), "All Available Authors");

    }

    @GetMapping("/find-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> findAuthorById(@RequestParam Long id) {
        return successResponse(authorService.findById(id), "User id-:" + id);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteAuthor(@RequestParam Long id) {
        return successResponse(authorService.deleteAuthor(id),"User id:-"+ id +" deleted");
    }
}
