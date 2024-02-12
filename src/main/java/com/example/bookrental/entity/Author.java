package com.example.bookrental.entity;


import com.example.bookrental.auditingconfig.AuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_author",uniqueConstraints = {@UniqueConstraint(columnNames = {"name","email","mobileNumber"})})
@SQLDelete(sql = "UPDATE tbl_author SET deleted = true WHERE author_id = ?")
@Where(clause ="deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author  extends AuditingEntity {
    @Id
    //Sequence Generator
    @SequenceGenerator(name = "author_primary_key_generator", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "author_primary_key_generator")
    Long authorId;

    String name;

    String email;

    String mobileNumber;
    private boolean deleted = Boolean.FALSE;
}
