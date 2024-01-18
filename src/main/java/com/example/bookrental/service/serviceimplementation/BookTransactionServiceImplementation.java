package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.Book;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.entity.Member;
import com.example.bookrental.enums.RentType;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.BookRepo;
import com.example.bookrental.repo.BookTransactionRepo;
import com.example.bookrental.repo.MembersRepo;
import com.example.bookrental.service.BookTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class BookTransactionServiceImplementation implements BookTransactionService {
    private final BookTransactionRepo bookTransactionRepo;
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;
    private final MembersRepo membersRepo;

    @Override
    public BookTransaction addTransaction(BookTransactionDto bookTransactionDto) {
        Long bookid = bookTransactionDto.getBookId();
        Book book = bookRepo.findById(bookid)
                .orElseThrow(() -> new NotFoundException("book not found"));

        Long memberid = bookTransactionDto.getFkMemberId();
        Member member = membersRepo.findById(memberid)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        if (book.getStock() <= 0) {
            throw new NotFoundException("Book is out of stock.");
        }
        if (bookTransactionDto.getRentType() == RentType.RENT) {
            book.setStock(book.getStock() - 1);
        }
        List<BookTransaction> bookTransactions = bookTransactionRepo.findAll();
        for (BookTransaction bookTransaction : bookTransactions) {
            Member existingMember = bookTransaction.getMember();
            if (existingMember.getMemberid().equals(bookTransactionDto.getFkMemberId()) && bookTransaction.getRentType().equals(RentType.RENT)) {
                throw new NotFoundException("Member cannot rent 2 books");
            }
        }
        BookTransaction bookTransaction = objectMapper.convertValue(bookTransactionDto, BookTransaction.class);
        bookTransaction.setMember(member);
        bookTransaction.setBook(book);

        return bookTransactionRepo.save(bookTransaction);
    }

    @Override
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto) {
        BookTransaction bookTransaction = bookTransactionRepo.findById(bookTransactionDto.getId())
                .orElseThrow(() -> new NotFoundException("Transaction Does not exist"));
        Optional<Book> updatedBookOptional = bookRepo.findById(bookTransactionDto.getBookId());
        Optional<Member> updatedMemberOptional = membersRepo.findById(bookTransactionDto.getFkMemberId());

        if (bookTransaction.getRentType() == RentType.RETURN) {
            deleteTransaction(bookTransactionDto.getId());
        }

        if (updatedMemberOptional.isPresent()) {
            Member updatemMember = updatedMemberOptional.get();
            bookTransaction.setMember(updatemMember);
        }

        if (updatedBookOptional.isPresent()) {
            Book updatedBook = updatedBookOptional.get();
            bookTransaction.setBook(updatedBook);
        }

        BeanUtils.copyProperties(bookTransactionDto, bookTransaction, getNullPropertyNames(bookTransactionDto));

        return bookTransactionRepo.save(bookTransaction);
    }


    @Override
    public List<BookTransaction> getAllTransaction() {
        return bookTransactionRepo.findAll();
    }

    @Override
    public BookTransaction findById(Long id) {
        return bookTransactionRepo.findById(id).orElseThrow(() -> new NotFoundException("Transaction Not available"));

    }

    @Transactional
    @Override
    public String deleteTransaction(Long id) {
        BookTransaction bookTransaction = bookTransactionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));
        bookTransactionRepo.delete(bookTransaction);
        Book book = bookTransaction.getBook();
        book.setStock(book.getStock() + 1);
        bookRepo.save(book);
        return bookTransaction.toString() + " Transaction has been deleted";
    }

    public List<Object> getNames() {
        return bookTransactionRepo.getMemberAndBookDetails();
    }

    public void generateExcel(HttpServletResponse response) throws IOException {
        List<BookTransaction> transactions = bookTransactionRepo.findAll();
        //Create workBook
        HSSFWorkbook workbook = new HSSFWorkbook();

        //create Sheet
        HSSFSheet sheet = workbook.createSheet("Transactions details");

        //create Row
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("BookName");
        row.createCell(3).setCellValue("FromDate");
        row.createCell(4).setCellValue("ToDate");

        //since index 0 has headers so data index start from 1
        int dataRow = 1;
        for (BookTransaction transaction : transactions) {
            //insert data to excelSheet
            HSSFRow data = sheet.createRow(dataRow);
            data.createCell(0).setCellValue(transaction.getId());
            data.createCell(1).setCellValue(transaction.getMember().getName());
            data.createCell(2).setCellValue(transaction.getBook().getName());
            data.createCell(3).setCellValue(transaction.getFromDate());
            data.createCell(4).setCellValue(transaction.getToDate());
            dataRow++;
        }
        ServletOutputStream out= response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();

    }
}