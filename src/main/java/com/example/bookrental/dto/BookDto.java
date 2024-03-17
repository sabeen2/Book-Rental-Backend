package com.example.bookrental.dto;

import com.example.bookrental.annotations.notzero.NotZeroNull;
import com.example.bookrental.annotations.publisheddate.PublishedDateValidator;
import com.example.bookrental.annotations.rating.ValidRating;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    Long id;
    @NotNull(message = "Name Cannot be empty")
    String name;

    @Positive
    @ValidRating
    Double rating;

    @NotNull(message = "Stock Cannot be empty")
    @NotZeroNull
    Integer stock;

    @PublishedDateValidator
    @NotNull(message = "date Cannot be empty")
    Date publishedDate;
    String photo;

    String isbn;

    @NotZeroNull
    Integer pages;
    @NotNull(message = "category id Cannot be empty")
    Long categoryId;
    @NotNull(message = "author id Cannot be empty")
    List<Long> authorId;
}
