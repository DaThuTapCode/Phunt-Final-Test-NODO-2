package com.trongphu.finalintern2.dto.product.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trongphu.finalintern2.dto.category.response.CategoryShortResponseDTO;

import com.trongphu.finalintern2.enumutil.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Trong Phu on 17/09/2024 23:06
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;

    private String name;

    private String img;

    private String productCode;

    private String description;

    private double price;

    private Long quantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate;

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private List<CategoryShortResponseDTO> categories;
}
