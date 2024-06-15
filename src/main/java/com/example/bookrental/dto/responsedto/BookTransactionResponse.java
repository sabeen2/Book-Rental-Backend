package com.example.bookrental.dto.responsedto;

import com.example.bookrental.enums.RentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BookTransactionResponse {
    Long id;

    Long code;

    String bookName;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date fromDate;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date toDate;

    RentType rentType;

    String memberName;
    Long fine;
}
