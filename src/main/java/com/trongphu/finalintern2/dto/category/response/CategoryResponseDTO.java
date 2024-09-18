package com.trongphu.finalintern2.dto.category.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trongphu.finalintern2.enumutil.CategoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;


/**
 * Created by Trong Phu on 17/09/2024 23:07
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long id;

    private String categoryCode;

    private String name;

    private String img;

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate;

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

}
