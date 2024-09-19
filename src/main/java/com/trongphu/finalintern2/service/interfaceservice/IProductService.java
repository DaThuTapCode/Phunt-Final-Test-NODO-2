package com.trongphu.finalintern2.service.interfaceservice;

import com.trongphu.finalintern2.dto.product.request.ProductRequestDTO;
import com.trongphu.finalintern2.dto.product.response.ProductResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * Created by Trong Phu on 17/09/2024 23:00
 *
 * @author Trong Phu
 */
public interface IProductService extends BaseService<Product, Long, ProductRequestDTO, ProductResponseDTO>{
    Page<ProductResponseDTO> searchProduct(PaginationObject paginationObject, String productCode, String name, Date startDate, Date endDate, Long categoryId);
}
