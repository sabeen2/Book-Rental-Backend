package com.example.bookrental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    Long id;
    @NotNull(message = "Name Cannot be empty")
    String  name;
    String discription;


}
