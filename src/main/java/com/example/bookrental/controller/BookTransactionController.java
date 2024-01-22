package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.repo.BookTransactionRepo;
import com.example.bookrental.service.serviceimplementation.BookTransactionServiceImplementation;
import com.example.bookrental.service.serviceimplementation.ReturnDateExceededEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lib/transactions")
@SecurityRequirement(name = "bookRental")
@Tag(name = "Book Transaction Controller", description = "APIs for managing Transactions")
public class BookTransactionController extends BaseController {
    private final BookTransactionServiceImplementation bookTransactionServiceImplementation;
    private final BookTransactionRepo bookTransactionRepo;
    private final ReturnDateExceededEmailService emailService;

    @Operation(summary = "Add book transaction", description = "Add book transaction to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book transaction added"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping("/add-transaction")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<BookTransaction> addTransaction(@RequestBody @Valid BookTransactionDto bookTransactionDto, HttpServletRequest request) {
        return successResponse(bookTransactionServiceImplementation.addTransaction(bookTransactionDto,request), "Transaction added");
    }

    @Operation(summary = "Get all book transaction", description = "Fetch all available book transaction detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available book transaction"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "book transaction not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/get-all-transactions")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<BookTransaction>> getAllTransaction() {
        return successResponse(bookTransactionServiceImplementation.getAllTransaction(), "All transactions");
    }

    @Operation(summary = "Get all book transaction details with rented member", description = "Fetch all available book transaction detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available book transaction"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "book transaction not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/all-transactions")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Object> getMemberAndBookDetails() {
        return successResponse(bookTransactionServiceImplementation.getNames(), "All transactions details");
    }

    @Operation(summary = "Get all book transaction details with rented member names, book names and download in excel", description = "Fetch all available book transaction detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/download-transactions")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> downloadExcel(HttpServletResponse response) throws IOException {
        return successResponse(bookTransactionServiceImplementation.generateExcel(response),"excel downloaded");
    }

    @Operation(summary = "Get transaction detail by book id", description = "Fetch available Transaction details based on provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Transaction Not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/get-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<BookTransaction> getById(@RequestParam Long id) {
        return successResponse(bookTransactionServiceImplementation.findById(id), "transaction detail-:" + id + " are");
    }

    @Operation(summary = "Update book transaction", description = "update the available book transaction detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book transaction updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "book transaction not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PutMapping("/update-transaction")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<BookTransaction> updateTransaction(@RequestBody BookTransactionDto bookTransactionDto,HttpServletRequest request) {
        return successResponse(bookTransactionServiceImplementation.updateTransaction(bookTransactionDto,request), "Transactions Updated");
    }

    @Operation(summary = "delete book transaction by id", description = "delete available book transaction detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book transaction found and Deleted"),
            @ApiResponse(responseCode = "402", description = "book transaction not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping("/delete-transaction")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteTransaction(@RequestParam Long id) {
        return successResponse(bookTransactionServiceImplementation.deleteTransaction(id), "Trascation" + id + " is hidden");
    }
    @Operation(summary = "send mail to users whose transaction date is exceeded", description = "send mail to transaction date is exceeded user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Email not sent"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping("/send-due-date-mail")
    public GenericResponse<String> sendDueDateExceededMail(){
        return successResponse(emailService.sendDueDateMail(),"mail sent");
    }
}
