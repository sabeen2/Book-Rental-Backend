package com.example.bookrental.dto;

import com.example.bookrental.enums.RENT_TYPE;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookTransactionDto {
    Long id;

    Long code;

    @JsonProperty("FK_book_id")
    long fkbookid;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date fromDate;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date toDate;

    @Enumerated(EnumType.STRING)
    RENT_TYPE rentType;

    @JsonProperty("Fk_member_id")
    Long fkMemberId;
}
