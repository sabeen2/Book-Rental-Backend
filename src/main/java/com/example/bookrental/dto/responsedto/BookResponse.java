package com.example.bookrental.dto.responsedto;

import com.example.bookrental.dto.AuthorDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BookResponse {
    Long id;
    String name;
    Double rating;
    Integer stock;
    @JsonFormat(pattern = "yyyy-mm-dd")
    Date publishedDate;
    String photo;
    String isbn;
    Integer pages;
    String categoryName;
//    List<String> authorName;
    List<AuthorDto> authorDetails;
    Long categoryId;
//    List<Long> authors;

}
