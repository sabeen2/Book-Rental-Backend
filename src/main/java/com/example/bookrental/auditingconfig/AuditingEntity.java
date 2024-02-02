package com.example.bookrental.auditingconfig;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditingEntity implements Serializable {
    @CreatedDate
    @Temporal(TIMESTAMP)
    Date createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    Date modifiedDate;

    @LastModifiedBy
    String modifiedBy;

    @CreatedBy
    String createdBy;
}
