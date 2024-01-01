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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;

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
        if (bookTransactionDto.getRent_type() == RENT_TYPE.RENT) {
            book.setStock(book.getStock() - 1);
        }

        List<BookTransaction> bookTransactions = bookTransactionRepo.findAll();
        for (BookTransaction bookTransaction : bookTransactions) {
            Member existingMember = bookTransaction.getMember();
            if (existingMember.getMemberid() == bookTransactionDto.getFk_member_id()) {
                throw new RuntimeException("member cannot rent 2 books");
            }
        }

        BookTransaction bookTransaction = objectMapper.convertValue(bookTransactionDto, BookTransaction.class);
        bookTransaction.setMember(member);
        bookTransaction.setBook(book);

        return bookTransactionRepo.save(bookTransaction);
    }

    @Transactional
    @Override
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto) {
        BookTransaction bookTransaction = bookTransactionRepo.findById(bookTransactionDto.getId()).orElseThrow(() -> new RuntimeException("Transaction Dosent exist"));
        if (bookTransactionDto.getRent_type() == RENT_TYPE.RETURN) {
            deleteTransaction(bookTransactionDto.getId(), bookTransactionDto.getRent_type());
            return bookTransaction;
        }

        Optional<Book> updatedBookOptional = bookRepo.findById(bookTransactionDto.getFK_book_id());
        Optional<Member> updatedMemberOptional = membersRepo.findById(bookTransactionDto.getFk_member_id());

        BeanUtils.copyProperties(bookTransactionDto, bookTransaction, getNullPropertyNames(bookTransactionDto));
        if (updatedMemberOptional.isPresent()) {
            Member updatemMember = updatedMemberOptional.get();
            bookTransaction.setMember(updatemMember);
        }

        if (updatedBookOptional.isPresent()) {
            Book updatedBook = updatedBookOptional.get();
            bookTransaction.setBook(updatedBook);
        }
        return bookTransactionRepo.save(bookTransaction);
    }


    @Override
    public List<BookTransaction> getAllTransaction() {
        return bookTransactionRepo.findAll();
    }

    @Override
    public String deleteTransaction(Long id, RENT_TYPE rentType) {
        BookTransaction bookTransaction = bookTransactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transcation Not Found"));
        bookTransaction.setRent_type(rentType);
        if (bookTransaction.getRent_type() == RENT_TYPE.RETURN) {
            bookTransactionRepo.delete(bookTransaction);
        } else {
            throw new RuntimeException("Invalid return type");
        }
        Book book = bookTransaction.getBook();
        book.setStock(book.getStock() + 1);
        bookRepo.save(book);
        return bookTransaction.toString() + " Transaction has been deleted";
    }
}