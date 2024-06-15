package com.example.bookrental.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_fine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FineEntity {
    @Id
    @SequenceGenerator(name = "fine_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "fine_generator")
    Long id;
    Long fineAmount;

    @OneToOne
    @JoinColumn(name = "transaction_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_refreshToken_usersEntity"))
    BookTransaction bookTransaction;

}
