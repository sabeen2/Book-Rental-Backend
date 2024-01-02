package com.example.bookrental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    Long id;
    String name;
    Double rating;
    Integer stock;
    @JsonFormat(pattern = "yyyy-mm-dd")
    Date publishedDate;
    String photo;
    Long categoryId;
    List<Long> authorId;
}
