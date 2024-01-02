package com.example.bookrental.service;
import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.enums.RENT_TYPE;

import java.util.List;

public interface BookTransactionService {
    public BookTransaction addTransaction  (BookTransactionDto bookTransactionDto);
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto);
    public List<BookTransaction> getAllTransaction();
    public String deleteTransaction (Long id, RENT_TYPE rentType);

}
