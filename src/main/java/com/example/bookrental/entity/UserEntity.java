package com.example.bookrental.entity;

import com.example.bookrental.auditingconfig.AuditingEntity;
import com.example.bookrental.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tbl_user",uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@SQLDelete(sql = "UPDATE tbl_user SET deleted = true WHERE author_id = ?")
@Where(clause = "deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "user_primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_primary_key_generator")
    Long id;
    @Column(unique = true)
    String username;
    String password;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    UserType userType;

    private boolean deleted = Boolean.FALSE;
}
