package com.trongphu.finalintern2.controller;

import com.trongphu.finalintern2.util.excel.ExcelExporter;
import com.trongphu.finalintern2.util.formater.FormatDateUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Trong Phu on 21/09/2024 15:05
 *
 * @author Trong Phu
 */
@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    private final ExcelExporter excelExporter;

    public ExcelController(ExcelExporter excelExporter) {
        this.excelExporter = excelExporter;
    }

    @GetMapping("/categories/{mode}")
    public ResponseEntity<InputStreamResource> exportCategoriesToExcel(
            @PathVariable int mode,
            @RequestParam(required = false) String categoryCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate
    ) throws IOException {

        ByteArrayInputStream byteArrayInputStream = excelExporter.exportCategoriesToExcel(mode, categoryCode, name, startDate, FormatDateUtil.setEndDate(endDate));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=categories.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/products/{mode}")
    public ResponseEntity<InputStreamResource> exportCategoriesSearchToExcel(
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(required = false) Long categoryID,
            @PathVariable int mode
    ) throws IOException {
        ByteArrayInputStream byteArrayInputStream = excelExporter.exportProductsToExcel(mode, productCode, name, startDate, FormatDateUtil.setEndDate(endDate), categoryID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=products.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(byteArrayInputStream));
    }


}
