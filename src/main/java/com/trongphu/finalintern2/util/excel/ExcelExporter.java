package com.trongphu.finalintern2.util.excel;

import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.entity.ProductCategory;
import com.trongphu.finalintern2.enumutil.ProductCategoryStatus;
import com.trongphu.finalintern2.exception.ResourceNotFoundException;
import com.trongphu.finalintern2.repository.CategoryRepository;
import com.trongphu.finalintern2.repository.ProductRepository;
import com.trongphu.finalintern2.util.formater.FormatDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Trong Phu on 19/09/2024 14:50
 *
 * @author Trong Phu
 */
@Component
public class ExcelExporter {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ExcelExporter(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    /**
     * Xuất  Category
     */
    public ByteArrayInputStream exportCategoriesToExcel(
            int mode,
            String categoryCode,
            String name,
            Date startDate,
            Date endDate
    ) throws IOException {
        // Lấy danh sách Category từ DB
         List<Category> categories = new ArrayList<>();
        if(mode == 1){
            categories =  categoryRepository.findAll();
        }else if (mode == 2){
            categories =  categoryRepository.searchPage(null, categoryCode, name, startDate, endDate).getContent();
        }

//        if(categories.isEmpty()) {
//            throw new ResourceNotFoundException("excel.error.categoryListIsEmpty", "");
//        }

        // Tạo Workbook và Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách Category");

        // Tạo Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Mã danh mục", "Tên", "Mô tả", "Ngày tạo", "Người tạo", "Ngày sửa cuối", "Người sửa cuối", "Trạng thái"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);

            // Tạo kiểu ô cho header
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerCellStyle.setFont(font);
            cell.setCellStyle(headerCellStyle);
        }

        // Điền dữ liệu vào bảng
        int rowNum = 1;
        for (Category category : categories) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(category.getId());
            row.createCell(1).setCellValue(category.getCategoryCode());
            row.createCell(2).setCellValue(category.getName());
            row.createCell(3).setCellValue(category.getDescription());
            row.createCell(4).setCellValue(FormatDateUtil.formatDateToString(category.getCreatedDate()));
            row.createCell(5).setCellValue(category.getCreatedBy());
            row.createCell(6).setCellValue(FormatDateUtil.formatDateToString(category.getModifiedDate()));
            row.createCell(7).setCellValue(category.getModifiedBy());
            row.createCell(8).setCellValue(category.getStatus().toString());
        }

        // Ghi workbook ra ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * Xuất sản phẩm
     */
    public ByteArrayInputStream exportProductsToExcel(
            int mode,
            String productCode,
            String name,
            Date startDate,
            Date endDate,
            Long categoryId
    ) throws IOException {
        List<Product> productList = new ArrayList<>();

        if(mode == 1){
            productList =  productRepository.findAll();
        }else if (mode == 2){
            productList =  productRepository.searchPage(null, productCode, name, startDate, endDate, categoryId).getContent();
        }
        // Tạo Workbook và Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách sản phẩm");

        // Tạo Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Mã sản phẩm", "Tên", "Mô tả", "Giá", "Số lượng", "Danh mục", "Ngày tạo", "Người tạo", "Ngày sửa cuối", "Người sửa cuối", "Trạng thái"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);

            // Tạo kiểu ô cho header
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerCellStyle.setFont(font);
            cell.setCellStyle(headerCellStyle);
        }

        // Điền dữ liệu vào bảng
        int rowNum = 1;
        for (Product product : productList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getProductCode());
            row.createCell(2).setCellValue(product.getName());
            row.createCell(3).setCellValue(product.getDescription());
            row.createCell(4).setCellValue(product.getPrice() + "");
            row.createCell(5).setCellValue(product.getQuantity() + "");
            row.createCell(6).setCellValue(product.getProductCategories().stream()
                    .filter(productCategory -> productCategory.getStatus().equals(ProductCategoryStatus.ACTIVE))
                    .map(ProductCategory::getCategory)
                    .map(Category::getCategoryCode)
                    .collect(Collectors.joining(", ")));
            row.createCell(7).setCellValue(FormatDateUtil.formatDateToString(product.getCreatedDate()));
            row.createCell(8).setCellValue(product.getCreatedBy());
            row.createCell(9).setCellValue(FormatDateUtil.formatDateToString(product.getModifiedDate()));
            row.createCell(10).setCellValue(product.getModifiedBy());
            row.createCell(11).setCellValue(product.getStatus().toString());
        }

        // Ghi workbook ra ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
