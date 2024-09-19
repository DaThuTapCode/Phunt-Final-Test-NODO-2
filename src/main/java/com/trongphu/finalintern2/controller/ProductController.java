package com.trongphu.finalintern2.controller;

import com.trongphu.finalintern2.dto.product.request.ProductRequestDTO;
import com.trongphu.finalintern2.dto.product.response.ProductResponseDTO;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import com.trongphu.finalintern2.service.interfaceservice.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Trong Phu on 17/09/2024 22:45
 *
 * @author Trong Phu
 */

@RestController
@RequestMapping(value = "api/product")
public class ProductController {

    // Service
    private final IProductService productService;

    //Constructor
    public ProductController(
            @Qualifier(value = "ProductService") IProductService productService
    ) {
        this.productService = productService;
    }

    /**
     * @apiNote API lấy ra toàn bộ Product*/
    @GetMapping(value = "")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    /**
     * @apiNote API tìm kiếm phân trang Product*/
    @GetMapping(value = "search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProduct(
            @RequestBody PaginationObject paginationObject,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(required = false) Long categoryId
    ) {
        return ResponseEntity.ok(productService.searchProduct(paginationObject, productCode, name, startDate, endDate, categoryId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping(value = "create")
    public ResponseEntity<ProductResponseDTO> createNewProduct(
            @ModelAttribute ProductRequestDTO productRequestDTO
            ){
        return ResponseEntity.ok(productService.create(productRequestDTO));
    }

    @PutMapping(value = "delete/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @ModelAttribute ProductRequestDTO productRequestDTO
    ){
        productService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
