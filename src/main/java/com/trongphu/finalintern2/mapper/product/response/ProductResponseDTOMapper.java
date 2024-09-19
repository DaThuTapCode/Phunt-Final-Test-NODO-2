package com.trongphu.finalintern2.mapper.product.response;

import com.trongphu.finalintern2.dto.category.response.CategoryShortResponseDTO;
import com.trongphu.finalintern2.dto.product.response.ProductResponseDTO;

import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.entity.ProductCategory;
import com.trongphu.finalintern2.mapper.BaseMapper;
import com.trongphu.finalintern2.mapper.category.response.CategoryShortResponseDTOMapper;
import com.trongphu.finalintern2.util.variabletp.VariableHehe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Trong Phu on 17/09/2024 23:09
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface ProductResponseDTOMapper extends BaseMapper<Product, ProductResponseDTO> {

    CategoryShortResponseDTOMapper categoryShortResponseDtoMapper = Mappers.getMapper(CategoryShortResponseDTOMapper.class);

    @Override
    @Mapping(source = "productCategories", target = "categories")
    @Mapping(source = "img", target = "img", qualifiedByName = "img")
    ProductResponseDTO toDTO(Product product);

    default List<CategoryShortResponseDTO> mapListCategoryShortResponseDTO(List<ProductCategory> productCategories) {
        if(productCategories == null){
            return null;
        }
        return productCategories.stream()
                .map(ProductCategory::getCategory)
                .map(categoryShortResponseDtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Named(value = "img")
    default String setUrlImg(String img){
        return VariableHehe.SERVER_PORT + "/images/" + img;
    }

}
