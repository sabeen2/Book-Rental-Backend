package com.example.bookrental.controller;

import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.enums.RENT_TYPE;
import com.example.bookrental.service.serviceimplementation.BookTransactionServiceImplementation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class BookTransactionController {
    private final BookTransactionServiceImplementation bookTransactionServiceImplementation;

    @PostMapping("/add-Transaction")
    public BookTransaction addTransaction(@RequestBody @Valid BookTransactionDto bookTransactionDto) {
        return bookTransactionServiceImplementation.addTransaction(bookTransactionDto);
    }

    @GetMapping("/get-All-Transcations")
    public List<BookTransaction> getAllTransaction(){
        return bookTransactionServiceImplementation.getAllTransaction();
    }
    @PutMapping("/update-Transcation")
    public BookTransaction updateTransaction(@RequestBody BookTransactionDto bookTransactionDto){
        return bookTransactionServiceImplementation.updateTransaction(bookTransactionDto);
    }
    @DeleteMapping("/delete-Transcation")
    public String deleteTransaction (@RequestParam Long id){
        return bookTransactionServiceImplementation.deleteTransaction(id);
    }
}
