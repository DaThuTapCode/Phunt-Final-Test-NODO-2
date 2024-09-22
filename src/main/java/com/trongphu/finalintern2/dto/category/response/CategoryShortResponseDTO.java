package com.trongphu.finalintern2.dto.category.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trongphu.finalintern2.enumutil.CategoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;

/**
 * Created by Trong Phu on 17/09/2024 23:07
 * <br>
 * Class này phụ trách việc hứng dữ liệu và trả về phía người dùng
 * <br>
 * Đã bị lược bỏ các mối quan hệ tránh đệ quy
 * @author Trong Phu
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryShortResponseDTO {
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
