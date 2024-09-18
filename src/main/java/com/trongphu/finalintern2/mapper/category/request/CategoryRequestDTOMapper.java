package com.trongphu.finalintern2.mapper.category.request;

import com.trongphu.finalintern2.dto.category.request.CategoryRequestDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 17/09/2024 23:07
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CategoryRequestDTOMapper extends BaseMapper<Category, CategoryRequestDTO> {
}
