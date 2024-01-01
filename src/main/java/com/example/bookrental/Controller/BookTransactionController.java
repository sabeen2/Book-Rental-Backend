package com.example.bookrental.Controller;

import com.example.bookrental.Dto.BookTransactionDto;
import com.example.bookrental.Entity.BookTransaction;
import com.example.bookrental.Enum.RENT_TYPE;
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
    @PutMapping("/updateTranscation")
    public BookTransaction updateTransaction(@RequestBody BookTransactionDto bookTransactionDto){
        return bookTransactionServiceImplementation.updateTransaction(bookTransactionDto);
    }
    @DeleteMapping("/deleteTranscation")
    public String deleteTransaction (@RequestParam Long id, RENT_TYPE rentType){
        return bookTransactionServiceImplementation.deleteTransaction(id, rentType);
    }
}
