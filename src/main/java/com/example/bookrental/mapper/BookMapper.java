package com.example.bookrental.mapper;

import com.example.bookrental.dto.BookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapper {
    @Select("select tb.id ,tb.name ,tb.photo ,tb.rating ,tb.stock ,tb.isbn ,tb.pages  from tbl_book tb")
    List<BookDto> getAllBooks();

    @Select("select tb.id ,tb.name ,tb.photo ,tb.rating ,tb.stock ,tb.isbn ,tb.pages  from tbl_book tb where id=#{id}")
    Optional<BookDto> getBookByID(@Param("id") Long id);
}
