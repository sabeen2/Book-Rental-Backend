package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.entity.FineEntity;
import com.example.bookrental.repo.BookTransactionRepo;
import com.example.bookrental.repo.FineRepo;
import com.example.bookrental.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {
    private final BookTransactionRepo bookTransactionRepo;
    private final FineRepo fineRepo;
    @Override
    @Scheduled(cron = "0 * * * * *")
    public void addFine() {
        List<BookTransaction> allTransactions = bookTransactionRepo.findAll();
        for (BookTransaction bookTransaction : allTransactions) {
            if (bookTransaction.isDeleted()) {
                continue;
            }
            LocalDate toDate = bookTransaction.getToDate();
            if (LocalDate.now().isAfter(toDate)) {
                long daysOverdue = ChronoUnit.DAYS.between(toDate, LocalDate.now());
                long fineAmount = daysOverdue * 5L;

                FineEntity fineEntity = fineRepo.findByBookTransaction(bookTransaction);
                if (fineEntity != null) {
                    fineEntity.setFineAmount(fineAmount);
                    fineRepo.save(fineEntity);
                } else {
                    fineEntity = FineEntity.builder()
                            .fineAmount(fineAmount)
                            .bookTransaction(bookTransaction)
                            .build();
                    fineRepo.save(fineEntity);
                }
            }
        }
    }
}
