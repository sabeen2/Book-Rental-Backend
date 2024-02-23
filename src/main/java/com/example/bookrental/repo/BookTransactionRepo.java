package com.example.bookrental.repo;

import com.example.bookrental.entity.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookTransactionRepo extends JpaRepository<BookTransaction,Long> {
    @Query("SELECT bt.rentType FROM BookTransaction bt WHERE bt.member.memberid = :memberId")
    String findRentTypeByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT m.name as Menber_name ,b.name as bookName ,bt.fromDate as fromDate,bt.toDate as to_Date FROM Member m inner JOIN BookTransaction bt on m.memberid=bt.member.memberid inner join Book b on b.id=bt.book.id")
    List<Map<String,Object>> getMemberAndBookDetails();

    @Query(value = "SELECT bt.id,bt.code,bt.from_date,bt.to_date, m.name as member_name, b.name from tbl_book_transaction bt\n" +
            " inner join tbl_member m on m.memberid=bt.memberid inner join tbl_book b on b.id=bt.book_id where bt.deleted='true' LIMIT ?1 OFFSET ?2 ",nativeQuery = true)
    List<Map<String,Object>> getTranscationHistry(int limit ,  int offset);

    List<BookTransaction> findByToDateBefore(Date date);

    List<BookTransaction> findByToDateAfter(Date date);
}
