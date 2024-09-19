package com.trongphu.finalintern2.mapper.category.response;

import com.trongphu.finalintern2.dto.category.response.CategoryShortResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.mapper.BaseMapper;
import com.trongphu.finalintern2.util.variabletp.VariableHehe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Created by Trong Phu on 18/09/2024 10:05
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CategoryShortResponseDTOMapper extends BaseMapper<Category, CategoryShortResponseDTO> {
    @Override
    @Mapping(target = "img", source = "img", qualifiedByName = "img")
    CategoryShortResponseDTO toDTO(Category category);
    @Named(value = "img")
    default String mapServerForImage(String img) {
        return VariableHehe.SERVER_PORT + "/images/" + img;
    }
}
