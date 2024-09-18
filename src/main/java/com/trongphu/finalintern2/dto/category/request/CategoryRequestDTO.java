package com.trongphu.finalintern2.dto.category.request;


import com.trongphu.finalintern2.util.groupsvalidator.CreateGroup;
import com.trongphu.finalintern2.util.groupsvalidator.UpdateGroup;
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

    @NotBlank(message = "NotBlank", groups = {CreateGroup.class})
    @Length(min = 10, max = 10, message = "Length", groups = {CreateGroup.class})
    private String categoryCode;

    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 3, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;

    private MultipartFile imgFile;

    @Length(min = 3, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    private String description;
}
