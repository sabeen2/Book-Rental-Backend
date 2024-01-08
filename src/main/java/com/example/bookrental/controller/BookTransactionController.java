package com.example.bookrental.controller;

import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.entity.Category;
import com.example.bookrental.enums.RENT_TYPE;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.serviceimplementation.BookTransactionServiceImplementation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Lib/transactions")
public class BookTransactionController {
    private final BookTransactionServiceImplementation bookTransactionServiceImplementation;

    @PostMapping("/add-Transaction")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<BookTransaction> addTransaction(@RequestBody @Valid BookTransactionDto bookTransactionDto) {
        return  GenericResponse.<BookTransaction>builder()
                .success(true)
                .message("Transaction added")
                .data(bookTransactionServiceImplementation.addTransaction(bookTransactionDto))
                .build();
    }
    @GetMapping("/get-All-Transcations")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<BookTransaction>> getAllTransaction(){
        return  GenericResponse.<List<BookTransaction>>builder()
                .success(true)
                .message("Transaction added")
                .data(bookTransactionServiceImplementation.getAllTransaction())
                .build();
    }
    @PutMapping("/update-Transcation")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<BookTransaction> updateTransaction(@RequestBody BookTransactionDto bookTransactionDto){

        return GenericResponse.<BookTransaction>builder()
                .success(true)
                .message("Transaction Updated")
                .data(bookTransactionServiceImplementation.updateTransaction(bookTransactionDto))
                .build();
    }
    @DeleteMapping("/delete-Transcation")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteTransaction (@RequestParam Long id){
        return GenericResponse.<String>builder()
                .success(true)
                .message("Trascation"+id+" is hidden")
                .data( bookTransactionServiceImplementation.deleteTransaction(id))
                .build();
    }
}
