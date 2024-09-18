package com.trongphu.finalintern2.entity;

import com.trongphu.finalintern2.enumutil.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Trong Phu on 17/09/2024 22:43
 * <br>
 * Đối tượng Category trong CSDL
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryCode;

    private String name;

    private String img;

    private Date createdDate;

    private Date modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY
    )
    private List<ProductCategory> productCategories;
}
