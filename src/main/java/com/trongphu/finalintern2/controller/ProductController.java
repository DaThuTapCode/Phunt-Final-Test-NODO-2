package com.trongphu.finalintern2.controller;

import com.trongphu.finalintern2.service.interfaceservice.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Trong Phu on 17/09/2024 22:45
 *
 * @author Trong Phu
 */

@RestController
@RequestMapping(value = "api/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(
            @Qualifier(value = "ProductService") IProductService productService
    ) {
        this.productService = productService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

}
