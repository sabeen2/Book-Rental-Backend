package com.example.bookrental.mapper;


import com.example.bookrental.dto.responsedto.BookTransactionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Mapper
public interface BookTransactionMapper {
    @Select("select tbt.id ,tbt.code ,tbt.from_date ,tbt.to_date,tbt.rent_status as rentType ,tm.name as memberName,tb.name as bookName from tbl_book_transaction tbt inner join tbl_member " +
            "tm on tbt.memberid =tm.memberid inner join tbl_book tb on tb.id =tbt.book_id where tbt.deleted =false")
    List<BookTransactionResponse> getBookTransactionDetails();

    @Select("select tbt.id ,tbt.code ,tbt.from_date ,tbt.to_date,tbt.rent_status as rentType ,tm.name as memberName ,tb.name as bookName from tbl_book_transaction tbt inner join tbl_member " +
            "tm on tbt.memberid =tm.memberid inner join tbl_book tb on tb.id =tbt.book_id  where tbt.id =#{id} and tbt.deleted =false")
    Optional<BookTransactionResponse> getById(@Param("id") Long id);


    @Select(value = "SELECT bt.id, bt.code, bt.from_date, bt.to_date, m.name AS member_name, b.name " +
            "FROM tbl_book_transaction bt " +
            "INNER JOIN tbl_member m ON m.memberid = bt.memberid " +
            "INNER JOIN tbl_book b ON b.id = bt.book_id " +
            "WHERE bt.deleted = 'true' " +
            "AND (DATE(bt.from_date) >= COALESCE(DATE(#{fromDate}), DATE(bt.from_date))) " +
            "AND (DATE(bt.to_date) <= COALESCE(DATE(#{toDate}), DATE(bt.to_date))) ")
    Page<BookTransactionResponse> getTransactionHistory(@Param("fromDate") Date fromDate,
                                                        @Param("toDate") Date toDate,
                                                        Pageable pageable);
}
