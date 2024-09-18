package com.trongphu.finalintern2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Created by Trong Phu on 18/09/2024 00:19
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Date createdDate;

    private Date modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
