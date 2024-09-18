package com.trongphu.finalintern2.service.impl;

import com.trongphu.finalintern2.dto.product.request.ProductRequestDTO;
import com.trongphu.finalintern2.dto.product.response.ProductResponseDTO;
import com.trongphu.finalintern2.mapper.product.response.ProductResponseDTOMapper;
import com.trongphu.finalintern2.repository.ProductRepository;
import com.trongphu.finalintern2.service.interfaceservice.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Trong Phu on 17/09/2024 23:00
 *
 * @author Trong Phu
 */
@Service(value = "ProductService")
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    private final ProductResponseDTOMapper productResponseDTOMapper;

    public ProductService(
            ProductRepository productRepository,
            ProductResponseDTOMapper productResponseDTOMapper
    ) {
        this.productRepository = productRepository;
        this.productResponseDTOMapper = productResponseDTOMapper;
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        return productResponseDTOMapper.toListDTO(productRepository.findAll());
    }

    @Override
    public ProductResponseDTO getById(Long aLong) {
        return null;
    }

    @Override
    public ProductResponseDTO getByCode(String code) {
        return null;
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public void softDelete(Long id) {

    }


}
