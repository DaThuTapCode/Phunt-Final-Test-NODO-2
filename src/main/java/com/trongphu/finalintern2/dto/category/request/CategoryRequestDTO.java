package com.trongphu.finalintern2.dto.category.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Trong Phu on 17/09/2024 23:06
 * <br>
 * Đối tượng hứng dữ liệu từ request dùng cho thêm sửa {@link com.trongphu.finalintern2.entity.Category}
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDTO {
//    private Long id;

    @NotBlank(message = "NotBlank")
    @Length(min = 10, max = 10, message = "Length")
    private String categoryCode;

    @NotBlank(message = "NotBlank")
    @Length(min = 3, max = 255, message = "Length")
    private String name;

    private MultipartFile imgFile;

    @Length(min = 3, max = 255, message = "Length")
    private String description;
}
