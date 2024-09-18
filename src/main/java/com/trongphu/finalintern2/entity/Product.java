package com.trongphu.finalintern2.entity;

import com.trongphu.finalintern2.enumutil.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Trong Phu on 17/09/2024 22:43
 *
 * @author Trong Phu
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String img;

    private String productCode;

    private String description;

    private double price;

    private Long quantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Date createdDate;

    private Date modifiedDate;

    private String createdBy;

    private String modifiedBy;

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY
    )
    private List<ProductCategory> productCategories;
}
