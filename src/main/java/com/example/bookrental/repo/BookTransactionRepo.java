package com.example.bookrental.repo;

import com.example.bookrental.entity.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import java.util.Date;
import java.util.List;

public interface BookTransactionRepo extends JpaRepository<BookTransaction,Long> {
    @Query("SELECT bt.rentType FROM BookTransaction bt WHERE bt.member.memberid = :memberId")
    String findRentTypeByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT m.name,b.name,bt.fromDate,bt.toDate FROM Member m inner JOIN BookTransaction bt on m.memberid=bt.member.memberid inner join Book b on b.id=bt.book.id")
    List<Object> getMemberAndBookDetails();

    List<BookTransaction> findByToDateBefore(Date date);
}
