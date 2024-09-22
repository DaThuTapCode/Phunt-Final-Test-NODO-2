package com.trongphu.finalintern2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
