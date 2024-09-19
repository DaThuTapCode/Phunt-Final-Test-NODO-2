package com.trongphu.finalintern2.mapper.product.response;

import com.trongphu.finalintern2.dto.product.response.ProductSearchResponseDTO;
import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 19/09/2024 10:13
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface ProductSearchResponseDTOMapper extends BaseMapper<Product, ProductSearchResponseDTO> {
}
