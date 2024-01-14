package com.example.bookrental.dto;

import com.example.bookrental.enums.RentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookTransactionDto {
    Long id;

    @NotNull(message = "Code Cannot be empty")
    Long code;

    @NotNull(message = "Book Id Cannot be empty")
//    @JsonProperty("FK_book_id")
    Long bookId;

    @NotNull(message = "date Cannot be empty")
    @JsonFormat(pattern = "yyyy-mm-dd")
    Date fromDate;

    @NotNull(message = "date Cannot be empty")
    @JsonFormat(pattern = "yyyy-mm-dd")
    Date toDate;

    @NotNull(message = "Rest status Cannot be empty")
    @Enumerated(EnumType.STRING)
    RentType rentType;

    @NotNull(message = "Member Cannot be empty")
    @JsonProperty("Fk_member_id")
    Long fkMemberId;
}
