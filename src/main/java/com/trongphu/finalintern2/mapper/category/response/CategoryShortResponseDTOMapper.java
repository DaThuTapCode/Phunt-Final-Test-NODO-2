package com.trongphu.finalintern2.mapper.category.response;

import com.trongphu.finalintern2.dto.category.response.CategoryShortResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 18/09/2024 10:05
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CategoryShortResponseDTOMapper extends BaseMapper<Category, CategoryShortResponseDTO> {
}
