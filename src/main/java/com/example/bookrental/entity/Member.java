package com.example.bookrental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tbl_member")
@SQLDelete(sql = "UPDATE tbl_member SET deleted = true WHERE memberid = ?")
@Where(clause ="deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Member {
    @Id
    @SequenceGenerator(name = "member_primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "member_primary_key_generator")
    Long memberid;
    @Email(message = "Email cannot be Empty")
    String email;
    @NotNull(message = "Cannot be empty")
    String name;
    @NotNull(message = "Cannot be empty")
    String mobileNo;
    String address;
    private boolean deleted = Boolean.FALSE;
}
