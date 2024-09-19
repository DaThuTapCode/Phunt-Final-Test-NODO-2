package com.trongphu.finalintern2.mapper.product.request;


import com.trongphu.finalintern2.dto.product.request.ProductRequestDTO;
import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 17/09/2024 23:08
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface ProductRequestDTOMapper extends BaseMapper<Product, ProductRequestDTO> {

}
