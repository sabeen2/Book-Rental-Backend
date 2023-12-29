package com.example.bookrental.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    long id;
    String name;
    double rating;
    int stock;
    @JsonFormat(pattern = "yyyy-mm-dd")
    Date published_date;
    String photo;
}
