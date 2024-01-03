package com.example.bookrental.repo;

import com.example.bookrental.entity.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookTransactionRepo extends JpaRepository<BookTransaction,Long> {
    @Query("SELECT bt.rentType FROM BookTransaction bt WHERE bt.member.memberid = :memberId")
    String findRentTypeByMemberId(@Param("memberId") Long memberId);

}
