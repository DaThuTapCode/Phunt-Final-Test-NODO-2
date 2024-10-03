package com.trongphu.finalintern2.dto.product.request;

import com.trongphu.finalintern2.util.groupsvalidator.CreateGroup;
import com.trongphu.finalintern2.util.groupsvalidator.UpdateGroup;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * Created by Trong Phu on 17/09/2024 23:06
 * <br>
 * Đối tượng hứng dữ liệu từ request
 *
 * @author Trong Phu
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "NotBlank", groups = CreateGroup.class)
    @Length(min = 10, max = 10, message = "Length", groups = CreateGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "productCodeInValid", groups = CreateGroup.class)
    private String productCode;

    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 3, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s]*$", message = "productNameInValid", groups = CreateGroup.class)
    private String name;

    private MultipartFile imgFile;

    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 3, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    private String description;

    @Min(value = 1, message = "Min", groups = {CreateGroup.class, UpdateGroup.class})
    @Max(value = 2000000000, message = "priceMax2000000000", groups = {CreateGroup.class, UpdateGroup.class})
    private double price;

    @Min(value = 1, message = "Min", groups = {CreateGroup.class, UpdateGroup.class})
    @Max(value = 2000000000, message = "quantityMax2000000000", groups = {CreateGroup.class, UpdateGroup.class})
    private Long quantity;

    private List<Long> categoryIds;
}
