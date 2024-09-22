package com.trongphu.finalintern2.controller;

import com.trongphu.finalintern2.config.i18n.Translator;
import com.trongphu.finalintern2.dto.product.request.ProductRequestDTO;
import com.trongphu.finalintern2.dto.product.response.ProductResponseDTO;
import com.trongphu.finalintern2.dto.product.response.ProductSearchResponseDTO;
import com.trongphu.finalintern2.objectshttp.ResponseDataObject;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import com.trongphu.finalintern2.service.interfaceservice.IProductService;
import com.trongphu.finalintern2.util.groupsvalidator.CreateGroup;
import com.trongphu.finalintern2.util.groupsvalidator.UpdateGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
     * @apiNote API lấy ra toàn bộ Product
     */
    @GetMapping(value = "")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    /**
     * @apiNote API tìm kiếm phân trang Product
     */
    @PostMapping(value = "search")
    public ResponseEntity<Page<ProductSearchResponseDTO>> searchProduct(
            @RequestBody PaginationObject paginationObject,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(required = false) Long categoryID
    ) {
        return ResponseEntity.ok(productService.searchProduct(paginationObject, productCode, name, startDate, endDate, categoryID));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping(value = "create")
    public ResponseEntity<ResponseDataObject<ProductResponseDTO>> createNewProduct(
            @ModelAttribute @Validated(value = CreateGroup.class) ProductRequestDTO productRequestDTO
    ) {
        ResponseDataObject<ProductResponseDTO> responseDataObject = new ResponseDataObject(HttpStatus.OK.value(), Translator.toLocale("product.created_successfully"), productService.create(productRequestDTO));
        return ResponseEntity.ok(responseDataObject);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ResponseDataObject> updateProduct(
            @PathVariable Long id,
            @ModelAttribute @Validated(value = UpdateGroup.class) ProductRequestDTO productRequestDTO
    ) {
        ResponseDataObject<ProductResponseDTO> responseDataObject = new ResponseDataObject(HttpStatus.OK.value(), Translator.toLocale("product.updated_successfully"), productService.update(id, productRequestDTO));
        return ResponseEntity.ok(responseDataObject);
    }

    @PutMapping(value = "delete/{id}")
    public ResponseEntity<ResponseDataObject> deleteProduct(
            @PathVariable Long id
    ) {
        productService.softDelete(id);
        ResponseDataObject<ProductResponseDTO> responseDataObject = new ResponseDataObject(HttpStatus.OK.value(), Translator.toLocale("product.deleted_successfully"));
        return ResponseEntity.ok(responseDataObject);
    }
}
