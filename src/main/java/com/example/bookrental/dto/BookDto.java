package com.example.bookrental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
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

    Double rating;

    @NotNull(message = "Stock Cannot be empty")
    Integer stock;

    Date publishedDate;

    String photo;

    @NotNull(message = "category id Cannot be empty")
    Long categoryId;

    @NotNull(message = "author id Cannot be empty")
    List<Long> authorId;
}
