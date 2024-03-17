package com.example.bookrental.mapper;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.dto.responsedto.BookResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapper {
    @Select("select  tb.id as id,\n" +
            "        tb.name ,tb.photo ,tb.rating ,tb.stock ,\n" +
            "        tc.id as categoryId,tb.published_date as publishedDate ,tb.isbn ,tb.pages,tc.name as categoryName\n" +
            "from tbl_book tb\n" +
            "         inner join tbl_catagory tc on tc.id=tb.category_id\n" +
            "         inner join book_author ba on ba.book_id =tb.id\n" +
            "         inner join tbl_author ta on ba.author_id =ta.author_id\n" +
            "where tb.deleted=false " +
            "GROUP BY tb.id, tb.name, tb.photo, tb.rating, tb.stock, tc.id, tb.published_date, tb.isbn, tb.pages, tc.name")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "authorDetails", column = "id", javaType = List.class, many = @Many(select = "getAuthorName")),
    })
    List<BookResponse> getAllBooks();
    @Select("select  tb.id as id,\n" +
            "        tb.name ,tb.photo ,tb.rating ,tb.stock ,\n" +
            "        tc.id as categoryId,tb.published_date as publishedDate ,tb.isbn ,tb.pages,tc.name as categoryName\n" +
            "from tbl_book tb\n" +
            "         inner join tbl_catagory tc on tc.id=tb.category_id\n" +
            "         inner join book_author ba on ba.book_id =tb.id\n" +
            "         inner join tbl_author ta on ba.author_id =ta.author_id\n" +
            "where tb.deleted=false AND tb.id=#{bid}\n" +
            "GROUP BY tb.id, tb.name, tb.photo, tb.rating, tb.stock, tc.id, tb.published_date, tb.isbn, tb.pages, tc.name")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "authorDetails", column = "id", javaType = List.class, many = @Many(select = "getAuthorName")),
    })
    List<BookResponse> getBookByID(@Param("bid") Long id);



    @Select("select ta.\"name\" ,ta.author_id from tbl_book tb  \n" +
            "inner join book_author ba on ba.book_id =tb.id \n" +
            "inner join tbl_author ta on ba.author_id =ta.author_id \n" +
            "where tb.id=#{bookId}")
    List<AuthorDto> getAuthorName(@Param("bookId") Long bookId);

    @Select("select ta.author_id from tbl_book tb  \n" +
            "inner join book_author ba on ba.book_id =tb.id \n" +
            "inner join tbl_author ta on ba.author_id =ta.author_id \n" +
            "where tb.id=#{bookId}")
    List<Long> getAuthorId(@Param("bookId") Long bookId);

}
