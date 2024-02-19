package com.example.bookrental.mapper;


import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.dto.responsedto.BookTransactionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookTransactionMapper {
    @Select("select tbt.id ,tbt.code ,tbt.from_date ,tbt.to_date,tbt.rent_status as rentType ,tm.name as memberName,tb.name as bookName from tbl_book_transaction tbt inner join tbl_member "+
            "tm on tbt.memberid =tm.memberid inner join tbl_book tb on tb.id =tbt.book_id where tbt.deleted =false")
    List<BookTransactionResponse> getBookTransactionDetails();

@Select("select tbt.id ,tbt.code ,tbt.from_date ,tbt.to_date,tbt.rent_status as rentType ,tm.name as memberName ,tb.name as bookName from tbl_book_transaction tbt inner join tbl_member "+
        "tm on tbt.memberid =tm.memberid inner join tbl_book tb on tb.id =tbt.book_id  where tbt.id =#{id} and tbt.deleted =false")
    Optional <BookTransactionResponse> getById(@Param("id") Long id);

}
