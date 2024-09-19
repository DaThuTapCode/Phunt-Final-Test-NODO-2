package com.trongphu.finalintern2.mapper.category.response;

import com.trongphu.finalintern2.dto.category.response.CategoryResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.mapper.BaseMapper;
import com.trongphu.finalintern2.util.variabletp.VariableHehe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Trong Phu on 17/09/2024 23:08
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CategoryResponseDTOMapper extends BaseMapper<Category, CategoryResponseDTO> {
    String SERVER = VariableHehe.SERVER_PORT;
    @Override
    @Mapping(target = "img", source = "img", qualifiedByName = "img")
    CategoryResponseDTO toDTO(Category category);

    @Named(value = "img")
    default String mapServerForImage(String img) {
        return SERVER + "/" + img;
    }
}
