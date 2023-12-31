package com.example.bookrental.Service.ServiceImplementation;

import com.example.bookrental.Dto.BookTransactionDto;
import com.example.bookrental.Entity.Book;
import com.example.bookrental.Entity.BookTransaction;
import com.example.bookrental.Entity.Member;
import com.example.bookrental.Enum.RENT_TYPE;
import com.example.bookrental.Repo.BookRepo;
import com.example.bookrental.Repo.BookTransactionRepo;
import com.example.bookrental.Repo.MembersRepo;
import com.example.bookrental.Service.BookTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookTransactionServiceImplementation implements BookTransactionService {
    private final BookTransactionRepo bookTransactionRepo;
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;
    private final MembersRepo membersRepo;

    @Override
    public BookTransaction addTransaction(BookTransactionDto bookTransactionDto) {
        long bookid = bookTransactionDto.getFK_book_id();
        Book book = bookRepo.findById(bookid)
                .orElseThrow(() -> new RuntimeException("book not found"));

        long memberid = bookTransactionDto.getFk_member_id();
        Member member = membersRepo.findById(memberid)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (book.getStock() <= 0) {
            throw new RuntimeException("Book is out of stock.");
        }

        if(bookTransactionDto.getRent_type()== RENT_TYPE.RENT){
            book.setStock(book.getStock()-1);
        }

        BookTransaction bookTransaction = objectMapper.convertValue(bookTransactionDto, BookTransaction.class);
        bookTransaction.setMember(member);
        bookTransaction.setBook(book);

        return bookTransactionRepo.save(bookTransaction);
    }
    @Override
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto) {
        return null;
    }

    @Override
    public List<BookTransaction> getAllTransaction() {
        return bookTransactionRepo.findAll();
    }



//delete all the records associated with the transcation
    @Override
    public String deleteTransaction(long id) {
        BookTransaction bookTransaction=bookTransactionRepo.findById(id).orElseThrow(()->new RuntimeException("Transcation Not Found"));
        bookTransactionRepo.delete(bookTransaction);
        return bookTransaction.toString()+" Transaction has been deleted";
    }
}