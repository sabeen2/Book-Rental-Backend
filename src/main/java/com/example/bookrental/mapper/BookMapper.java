package com.example.bookrental.mapper;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.dto.responsedto.BookResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapper {
    @Select("select tb.id ,tb.name ,tb.photo ,tb.rating ,tb.stock ,tb.published_date as publishedDate ,tb.isbn ,tb.pages,tc.name as categoryName,\n" +
            " STRING_AGG(ta.name, ', ') as authorName \n" +
            " from tbl_book tb \n" +
            " inner join tbl_catagory tc on tc.id  =tb.category_id \n" +
            " inner join book_author ba on ba.book_id =tb.id \n" +
            " inner join tbl_author ta on ba.author_id =ta.author_id \n" +
            " where tb.deleted =false  GROUP BY tb.id, tb.name, tb.photo, tb.rating, tb.stock, tb.published_date, tb.isbn, tb.pages, tc.name  ")
    List<BookResponse> getAllBooks();

    @Select("select tb.id ,tb.name ,tb.photo ,tb.rating ,tb.published_date as publishedDate,tb.stock ,tb.isbn ,tb.pages,tc.name as categoryName,\n" +
            " ta.name as authorName  from tbl_book tb \n" +
            " inner join tbl_catagory tc on tc.id  =tb.category_id \n" +
            " inner join book_author ba on ba.book_id =tb.id \n" +
            " inner join tbl_author ta on ba.author_id =ta.author_id \n" +
            " where tb.deleted =false and tb.id=#{id}")
    Optional<BookResponse> getBookByID(@Param("id") Long id);
}
