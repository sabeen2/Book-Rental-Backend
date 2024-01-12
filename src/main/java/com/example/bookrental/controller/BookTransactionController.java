package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
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
public class BookTransactionController extends BaseController {
    private final BookTransactionServiceImplementation bookTransactionServiceImplementation;

    @PostMapping("/add-Transaction")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<BookTransaction> addTransaction(@RequestBody @Valid BookTransactionDto bookTransactionDto) {
        return successResponse(bookTransactionServiceImplementation.addTransaction(bookTransactionDto), "Transaction added");
    }

    @GetMapping("/get-All-Transcations")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<BookTransaction>> getAllTransaction() {
        return successResponse(bookTransactionServiceImplementation.getAllTransaction(), "All transactions");
    }

    @PutMapping("/update-Transcation")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<BookTransaction> updateTransaction(@RequestBody BookTransactionDto bookTransactionDto) {
        return successResponse(bookTransactionServiceImplementation.updateTransaction(bookTransactionDto), "Transactions Updated");
    }

    @DeleteMapping("/delete-Transcation")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteTransaction(@RequestParam Long id) {
        return successResponse(bookTransactionServiceImplementation.deleteTransaction(id),"Trascation" + id + " is hidden");
    }
}
