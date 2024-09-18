package com.trongphu.finalintern2.controller;

import com.trongphu.finalintern2.dto.category.request.CategoryRequestDTO;
import com.trongphu.finalintern2.dto.category.response.CategoryResponseDTO;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import com.trongphu.finalintern2.service.interfaceservice.ICategoryService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trongphu.finalintern2.entity.Category;

import java.util.Date;

/**
 * Created by Trong Phu on 17/09/2024 23:36
 *
 * @author Trong Phu
 */
@RestController
@RequestMapping(value = "api/category")
public class CategoryController {

    //Service
    private final ICategoryService categoryService;

    //Constructor
    public CategoryController(
            @Qualifier(value = "CategoryService") ICategoryService categoryService
    ) {
        this.categoryService = categoryService;
    }

    /**
     * @apiNote API lấy ra toàn bộ danh sách category
     * GET http://localhost:8080/api/category
     */
    @GetMapping(value = "")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    /**
     * @apiNote lấy {@link Category} theo id GET http://localhost:8080/api/category/{{id}}*/
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    /**
     * @param categoryRequestDTO đối tượng {@link CategoryRequestDTO} hứng dữ liệu từ request
     * @apiNote : API thêm {@link Category} mới POST http://localhost:8080/api/category/create
     */
    @PostMapping(value = "create")
    public ResponseEntity<CategoryResponseDTO> create(
            @ModelAttribute CategoryRequestDTO categoryRequestDTO
    ) {
        return ResponseEntity.ok(categoryService.create(categoryRequestDTO));
    }

    /**
     * @apiNote : API Phân trang tìm kiếm POST http://localhost:8080/api/category/search-page
     */
    @PostMapping(value = "search-page")
    public ResponseEntity<Page<CategoryResponseDTO>> searchPage(
            @RequestBody PaginationObject paginationObject,
            @RequestParam(required = false) String categoryCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate
    ) {
        return ResponseEntity.ok(categoryService.searchPage(paginationObject, categoryCode, name, startDate, endDate));
    }
}
