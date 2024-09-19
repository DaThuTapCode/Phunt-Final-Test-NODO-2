package com.trongphu.finalintern2.mapper.product.response;

import com.trongphu.finalintern2.dto.product.response.ProductSearchResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.entity.ProductCategory;
import com.trongphu.finalintern2.mapper.BaseMapper;
import com.trongphu.finalintern2.util.variabletp.VariableHehe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Trong Phu on 19/09/2024 10:13
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface ProductSearchResponseDTOMapper extends BaseMapper<Product, ProductSearchResponseDTO> {
    @Override
    @Mapping(target = "img", source = "img", qualifiedByName = "img")
    @Mapping(target = "categories", source = "productCategories", qualifiedByName = "cate")
    ProductSearchResponseDTO toDTO(Product product);

    @Named(value = "img")
    default String setUrlImage(String img){
        return VariableHehe.SERVER_PORT + "/images/" + img;
    }
    @Named(value = "cate")
    default String setStringCategories(List<ProductCategory> list){
        return list.stream()
                .map(ProductCategory::getCategory)
                .map(Category::getCategoryCode)
                .collect(Collectors.joining(", "));
    }
}
