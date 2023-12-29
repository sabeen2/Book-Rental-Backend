package com.example.bookrental.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatagoryDto {
    long id;
    String  name;
    String discription;
}
