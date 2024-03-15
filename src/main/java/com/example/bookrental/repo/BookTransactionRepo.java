package com.example.bookrental.repo;

import com.example.bookrental.entity.BookTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookTransactionRepo extends JpaRepository<BookTransaction, Long> {
    @Query("SELECT bt.rentType FROM BookTransaction bt WHERE bt.member.memberid = :memberId")
    String findRentTypeByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT m.name as Menber_name ,b.name as bookName ,bt.fromDate as fromDate,bt.toDate as to_Date FROM Member m inner JOIN BookTransaction bt on m.memberid=bt.member.memberid inner join Book b on b.id=bt.book.id")
    List<Map<String, Object>> getMemberAndBookDetails();

    @Query(value = "SELECT bt.id, bt.code, DATE (bt.from_date) as fromDate, Date(bt.to_date) as toDate, m.name AS member_name, b.name " +
            "FROM tbl_book_transaction bt " +
            "INNER JOIN tbl_member m ON m.memberid = bt.memberid " +
            "INNER JOIN tbl_book b ON b.id = bt.book_id " +
            "WHERE bt.deleted = 'true' " +
            "AND (DATE(bt.from_date) >= COALESCE(DATE(?1), DATE(bt.from_date))) " +
            "AND (DATE(bt.to_date) <= COALESCE(DATE(?2), DATE(bt.to_date))) ",
            nativeQuery = true)
    Page<Map<String, Object>> getTransactionHistory(@Param("fromDate") Date fromDate,
                                                    @Param("toDate") Date toDate,
                                                    Pageable pageable);

    @Query(value = "select count(*) from tbl_book_transaction tbt  where tbt.deleted =true", nativeQuery = true)
    int transactionCount();

    List<BookTransaction> findByToDateBefore(Date date);

    List<BookTransaction> findByToDateAfter(Date date);
}
