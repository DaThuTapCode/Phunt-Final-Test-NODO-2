package com.trongphu.finalintern2.service.impl;

import com.trongphu.finalintern2.dto.product.request.ProductRequestDTO;
import com.trongphu.finalintern2.dto.product.response.ProductResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.entity.ProductCategory;
import com.trongphu.finalintern2.enumutil.ProductCategoryStatus;
import com.trongphu.finalintern2.enumutil.ProductStatus;
import com.trongphu.finalintern2.exception.ResourceNotFoundException;
import com.trongphu.finalintern2.exception.file.FileUploadErrorException;
import com.trongphu.finalintern2.mapper.product.request.ProductRequestDTOMapper;
import com.trongphu.finalintern2.mapper.product.response.ProductResponseDTOMapper;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import com.trongphu.finalintern2.repository.CategoryRepository;
import com.trongphu.finalintern2.repository.ProductCategoryRepository;
import com.trongphu.finalintern2.repository.ProductRepository;
import com.trongphu.finalintern2.service.interfaceservice.IProductService;
import com.trongphu.finalintern2.util.file.FileUpLoadUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Trong Phu on 17/09/2024 23:00
 *
 * @author Trong Phu
 */
@Service(value = "ProductService")
public class ProductService implements IProductService {
    // Repo
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    // Mapper response
    private final ProductResponseDTOMapper productResponseDTOMapper;

    // Mapper request
    private final ProductRequestDTOMapper productRequestDTOMapper;

    // Constructor
    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductCategoryRepository productCategoryRepository,
            ProductResponseDTOMapper productResponseDTOMapper,
            ProductRequestDTOMapper productRequestDTOMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productResponseDTOMapper = productResponseDTOMapper;
        this.productRequestDTOMapper = productRequestDTOMapper;
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        return productResponseDTOMapper.toListDTO(productRepository.findAll());
    }

    @Override
    public ProductResponseDTO getById(Long aLong) {
        Product productE = productRepository.findById(aLong).orElseThrow(() -> new ResourceNotFoundException("exception.ResourceNotFoundException", Product.class.getSimpleName()));
        return productResponseDTOMapper.toDTO(productE);
    }

    @Override
    public ProductResponseDTO getByCode(String code) {
        return null;
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        Product product = productRequestDTOMapper.toEntity(productRequestDTO);
        //Kiểm tra xem có id của các category đi kèm không
        setProductCategory(product, productRequestDTO.getCategoryIds());
        //Fix cứng các trường
        product.setCreatedBy("ADMIN-NTP");
        product.setModifiedBy("ADMIN-NTP");
        product.setCreatedDate(new Date());
        product.setModifiedDate(new Date());
        product.setStatus(ProductStatus.ACTIVE);

        //Thêm ảnh vào sản phẩm
        handleUpLoadFile(product, productRequestDTO.getImgFile(), FileUpLoadUtil.FILE_TYPE_IMAGE, 10L);

        //Save
        Product productSaved = productRepository.save(product);
        return productResponseDTOMapper.toDTO(productSaved);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product productExisting = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.ResourceNotFoundException", Product.class.getSimpleName()));
        Product productToUpdate = productRequestDTOMapper.toEntity(productRequestDTO);

        //Giữ lại các trường không cần cập nhật


        return null;
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.ResourceNotFoundException", Product.class.getSimpleName()));
        productRepository.softDelete(id);
    }

    @Override
    public Page<ProductResponseDTO> searchProduct(PaginationObject paginationObject, String productCode, String name, Date startDate, Date endDate, Long categoryId) {
        return null;
    }

    void setProductCategory(Product product, List<Long> listIdCategory) {
        if (listIdCategory != null || !listIdCategory.isEmpty()) {
            List<Category> categoryList = categoryRepository.findAllById(listIdCategory);
            List<ProductCategory> productCategories = categoryList.stream()
                    .map(category -> ProductCategory
                            .builder()
                            .product(product)
                            .category(category)
                            .createdBy("ADMIN-NTP")
                            .modifiedBy("ADMIN-NTP")
                            .createdDate(new Date())
                            .modifiedDate(new Date())
                            .status(ProductCategoryStatus.ACTIVE)
                            .build()).collect(Collectors.toList());
            product.setProductCategories(productCategories);
        }
    }

    void handleUpLoadFile(Product product, MultipartFile file, String conditionType, Long conditionSize) {
        try {
            product.setImg(FileUpLoadUtil.uploadFile(file, conditionType, conditionSize));
        } catch (IOException e) {
            throw new FileUploadErrorException("exception_file.FileUploadErrorException");
        }
    }
}
