package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.dto.responsedto.BookTransactionResponse;
import com.example.bookrental.dto.responsedto.PaginationResponse;
import com.example.bookrental.entity.Book;
import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.entity.Member;
import com.example.bookrental.enums.RentType;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.mapper.BookTransactionMapper;
import com.example.bookrental.repo.BookRepo;
import com.example.bookrental.repo.BookTransactionRepo;
import com.example.bookrental.repo.MembersRepo;
import com.example.bookrental.service.BookTransactionService;
import com.example.bookrental.utils.CustomPagination;
import com.example.bookrental.utils.ExcelToDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class BookTransactionServiceImplementation implements BookTransactionService {
    private final BookTransactionRepo bookTransactionRepo;
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;
    private final MembersRepo membersRepo;
    private final BookTransactionMapper bookTransactionMapper;
private final CustomPagination customPagination;
    private final CustomMessageSource messageSource;

    @Override
    public String addTransaction(BookTransactionDto bookTransactionDto, HttpServletRequest request) {
        Long bookId = bookTransactionDto.getBookId();
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

        Long memberId = bookTransactionDto.getFkMemberId();
        Member member = membersRepo.findById(memberId)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

        if (member.isDeleted()) {
            throw new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode()));
        }

        if (book.getStock() <= 0) {
            throw new NotFoundException(messageSource.get(ExceptionMessages.OUT_OF_STOCK.getCode()));
        }
        if (bookTransactionDto.getRentType() == RentType.RENT) {
            book.setStock(book.getStock() - 1);
        }
        List<BookTransaction> bookTransactions = bookTransactionRepo.findAll();
        for (BookTransaction bookTransaction : bookTransactions) {
            Member existingMember = bookTransaction.getMember();
            if(!bookTransaction.isDeleted()) {
                if (existingMember.getMemberid().equals(bookTransactionDto.getFkMemberId()) && bookTransaction.getRentType().equals(RentType.RENT)) {
                    throw new NotFoundException(messageSource.get(ExceptionMessages.MULTIPLE_RENT.getCode()));
                }
            }
        }
        BookTransaction bookTransaction = objectMapper.convertValue(bookTransactionDto, BookTransaction.class);
        bookTransaction.setMember(member);
        bookTransaction.setBook(book);
        bookTransaction.setCode(String.valueOf(generateRandomNumber()));
        bookTransactionRepo.save(bookTransaction);
        return messageSource.get(ExceptionMessages.SAVE.getCode()) + bookTransaction.getCode();

    }

    @Override
    public String updateTransaction(BookTransactionDto bookTransactionDto) {
        BookTransaction bookTransaction = bookTransactionRepo.findById(bookTransactionDto.getId())
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

        if (bookTransactionDto.getRentType() == RentType.RETURN) {
            deleteTransaction(bookTransactionDto.getId());
        }
//        Optional<Book> updatedBookOptional = bookRepo.findById(bookTransactionDto.getBookId());
//        Optional<Member> updatedMemberOptional = membersRepo.findById(bookTransactionDto.getFkMemberId());

        if (bookTransactionDto.getFkMemberId() != null) {
            Member updatemMember = membersRepo.findById(bookTransactionDto.getFkMemberId()).get();
            bookTransaction.setMember(updatemMember);
        }

        if (bookTransactionDto.getBookId() != null) {
            Book updatedBook = bookRepo.findById(bookTransactionDto.getBookId()).get();
            bookTransaction.setBook(updatedBook);
        }

        BeanUtils.copyProperties(bookTransactionDto, bookTransaction, getNullPropertyNames(bookTransactionDto));
        bookTransactionRepo.save(bookTransaction);
        return messageSource.get(ExceptionMessages.UPDATE.getCode()) + bookTransactionDto.getId();
    }


    @Override
    public List<BookTransactionResponse> getAllTransaction() {
        return bookTransactionMapper.getBookTransactionDetails();
    }

    @Override
    public BookTransactionResponse findById(Long id) {
        return bookTransactionMapper.getById(id).orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

    }

    @Transactional
    @Override
    public String deleteTransaction(Long id) {
        BookTransaction bookTransaction = bookTransactionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
//        bookTransactionRepo.delete(bookTransaction);
        Book book = bookTransaction.getBook();
        book.setStock(book.getStock() + 1);
        bookRepo.save(book);
        bookTransaction.setDeleted(true);
        bookTransaction.setRentType(RentType.RETURN);
        bookTransaction.setToDate(new Date());
        bookTransactionRepo.save(bookTransaction);
        return bookTransaction.getId() + messageSource.get(ExceptionMessages.DELETED.getCode());
    }

    public List<BookTransactionResponse> getNames() {
        return bookTransactionMapper.getBookTransactionDetails();
    }

    public PaginationResponse getTransactionHistory(Date fromDate, Date toDate, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return customPagination.getPaginatedData(bookTransactionRepo.getTransactionHistory(fromDate, toDate, pageable));
//        return bookTransactionRepo.getTransactionHistory(fromDate, toDate, pageable);
    }

    public String generateExcel(HttpServletResponse response) throws IOException {
//        workbook-->sheet-->row-->cell-->{DATA}
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
            data.createCell(3).setCellValue(transaction.getFromDate().toString());
            data.createCell(4).setCellValue(transaction.getToDate().toString());
            dataRow++;
        }
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=transactions.xls";
        response.setHeader(headerKey, headerValue);
        out.close();
        return messageSource.get(ExceptionMessages.DOWNLOADED.getCode());
    }

    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<BookTransaction> bookTransactions = ExcelToDb.createExcel(file, BookTransaction.class);
        bookTransactionRepo.saveAll(bookTransactions);
        return messageSource.get(ExceptionMessages.EXPORT_EXCEL_SUCCESS.getCode());
    }

    public  long generateRandomNumber() {
        // Create an instance of Random class
        Random random = new Random();
        // Generate a random long between 100000 and 999999 (inclusive)
        long min = 100000L;
        long max = 999999L;
        return min + (long) (random.nextDouble() * (max - min));
    }


    public int getTransactionCount(){
       return bookTransactionRepo.transactionCount();
    }
}