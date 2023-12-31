package com.example.bookrental.Dto;

import com.example.bookrental.Enum.RENT_TYPE;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookTransactionDto {
    long id;

    long code;

    @JsonProperty("FK_book_id")
    long FK_book_id;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date from_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date to_date;

    @Enumerated(EnumType.STRING)
    RENT_TYPE rent_type;


    @JsonProperty( "Fk_member_id")
    Long Fk_member_id;
}
