package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.Book;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.entity.Member;
import com.example.bookrental.enums.RENT_TYPE;
import com.example.bookrental.repo.BookRepo;
import com.example.bookrental.repo.BookTransactionRepo;
import com.example.bookrental.repo.MembersRepo;
import com.example.bookrental.service.BookTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class BookTransactionServiceImplementation implements BookTransactionService {
    private final BookTransactionRepo bookTransactionRepo;
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;
    private final MembersRepo membersRepo;

    @Override
    public BookTransaction addTransaction(BookTransactionDto bookTransactionDto) {
        Long bookid = bookTransactionDto.getFkbookid();
        Book book = bookRepo.findById(bookid)
                .orElseThrow(() -> new RuntimeException("book not found"));

        Long memberid = bookTransactionDto.getFkMemberId();
        Member member = membersRepo.findById(memberid)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (book.getStock() <= 0) {
            throw new RuntimeException("Book is out of stock.");
        }
        if (bookTransactionDto.getRentType() == RENT_TYPE.RENT) {
            book.setStock(book.getStock() - 1);
        }

        List<BookTransaction> bookTransactions = bookTransactionRepo.findAll();
        for (BookTransaction bookTransaction : bookTransactions) {
            Member existingMember = bookTransaction.getMember();
            if (existingMember.getMemberid().equals(bookTransactionDto.getFkMemberId())) {
                throw new RuntimeException("member cannot rent 2 books");
            }
        }

        BookTransaction bookTransaction = objectMapper.convertValue(bookTransactionDto, BookTransaction.class);
        bookTransaction.setMember(member);
        bookTransaction.setBook(book);

        return bookTransactionRepo.save(bookTransaction);
    }

    @Override
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto) {
        BookTransaction bookTransaction = bookTransactionRepo.findById(bookTransactionDto.getId()).orElseThrow(() -> new RuntimeException("Transaction Dosent exist"));
        if (bookTransactionDto.getRentType() == RENT_TYPE.RETURN) {
            deleteTransaction(bookTransactionDto.getId(), bookTransactionDto.getRentType());
            return bookTransaction;
        }
        Optional<Book> updatedBookOptional = bookRepo.findById(bookTransactionDto.getFkbookid());
        Optional<Member> updatedMemberOptional = membersRepo.findById(bookTransactionDto.getFkMemberId());

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
        bookTransaction.setRentType(rentType);
        if (bookTransaction.getRentType() == RENT_TYPE.RETURN) {
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