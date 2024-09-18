package com.trongphu.finalintern2.mapper.category.response;

import com.trongphu.finalintern2.dto.category.response.CategoryResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 17/09/2024 23:08
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CategoryResponseDTOMapper extends BaseMapper<Category, CategoryResponseDTO> {
}
