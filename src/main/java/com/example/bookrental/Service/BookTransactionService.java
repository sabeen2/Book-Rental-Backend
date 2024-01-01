package com.example.bookrental.Service;
import com.example.bookrental.Dto.BookTransactionDto;
import com.example.bookrental.Entity.BookTransaction;
import com.example.bookrental.Enum.RENT_TYPE;

import java.util.List;

public interface BookTransactionService {
    public BookTransaction addTransaction  (BookTransactionDto bookTransactionDto);
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto);
    public List<BookTransaction> getAllTransaction();
    public String deleteTransaction (Long id, RENT_TYPE rentType);

}
