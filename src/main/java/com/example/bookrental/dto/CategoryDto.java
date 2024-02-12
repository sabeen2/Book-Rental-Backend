package com.example.bookrental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    Long id;
    @NotNull(message = "Name Cannot be empty")
    String  name;
    String discription;

}
