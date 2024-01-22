package com.example.bookrental.entity;

import com.example.bookrental.auditingconfig.AuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tbl_member",uniqueConstraints = {@UniqueConstraint(columnNames = {"email","mobileNo"})})
@SQLDelete(sql = "UPDATE tbl_member SET deleted = true WHERE memberid = ?")
@Where(clause = "deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Member extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "member_primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "member_primary_key_generator")
    Long memberid;

    String email;
    String name;

    String mobileNo;
    String address;
    private boolean deleted = Boolean.FALSE;
}
