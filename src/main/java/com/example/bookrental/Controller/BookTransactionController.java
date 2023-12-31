package com.example.bookrental.Controller;

import com.example.bookrental.Dto.BookTransactionDto;
import com.example.bookrental.Entity.BookTransaction;
import com.example.bookrental.Service.ServiceImplementation.BookTransactionServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class BookTransactionController {
    private final BookTransactionServiceImplementation bookTransactionServiceImplementation;

    @PostMapping("/addTransaction")
    public BookTransaction addTransaction(@RequestBody BookTransactionDto bookTransactionDto) {
        return bookTransactionServiceImplementation.addTransaction(bookTransactionDto);
    }

    @GetMapping("/getAllTranscations")
    public List<BookTransaction> getAllTransaction(){
        return bookTransactionServiceImplementation.getAllTransaction();
    }
    @DeleteMapping("/deleteTranscation")
    public String deleteTransaction (@RequestParam long id){
        return bookTransactionServiceImplementation.deleteTransaction(id);
    }
}
