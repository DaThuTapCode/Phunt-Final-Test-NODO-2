package com.trongphu.finalintern2.service.impl;

import com.trongphu.finalintern2.dto.product.request.ProductRequestDTO;
import com.trongphu.finalintern2.dto.product.response.ProductResponseDTO;
import com.trongphu.finalintern2.dto.product.response.ProductSearchResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.entity.Product;
import com.trongphu.finalintern2.entity.ProductCategory;
import com.trongphu.finalintern2.enumutil.ProductCategoryStatus;
import com.trongphu.finalintern2.enumutil.ProductStatus;
import com.trongphu.finalintern2.exception.ResourceNotFoundException;
import com.trongphu.finalintern2.exception.file.FileUploadErrorException;
import com.trongphu.finalintern2.mapper.product.request.ProductRequestDTOMapper;
import com.trongphu.finalintern2.mapper.product.response.ProductResponseDTOMapper;
import com.trongphu.finalintern2.mapper.product.response.ProductSearchResponseDTOMapper;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import com.trongphu.finalintern2.repository.CategoryRepository;
import com.trongphu.finalintern2.repository.ProductCategoryRepository;
import com.trongphu.finalintern2.repository.ProductRepository;
import com.trongphu.finalintern2.service.interfaceservice.IProductService;
import com.trongphu.finalintern2.util.file.FileUpLoadUtil;
import com.trongphu.finalintern2.util.formater.FormatDateUtil;
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
    private final ProductSearchResponseDTOMapper productSearchResponseDTOMapper;
    // Mapper request
    private final ProductRequestDTOMapper productRequestDTOMapper;

    // Constructor
    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductCategoryRepository productCategoryRepository,
            ProductResponseDTOMapper productResponseDTOMapper,
            ProductSearchResponseDTOMapper productSearchResponseDTOMapper,
            ProductRequestDTOMapper productRequestDTOMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productResponseDTOMapper = productResponseDTOMapper;
        this.productSearchResponseDTOMapper = productSearchResponseDTOMapper;
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
        setHardDataCreateProduct(product);
        //Thêm ảnh vào sản phẩm
        handleUpLoadFile(product, productRequestDTO.getImgFile(), FileUpLoadUtil.FILE_TYPE_IMAGE, 10L);
        //Save
        Product productSaved = productRepository.save(product);
        return productResponseDTOMapper.toDTO(productSaved);
    }


    void setHardDataCreateProduct(Product product) {
        product.setCreatedBy("ADMIN-NTP");
        product.setModifiedBy("ADMIN-NTP");
        product.setCreatedDate(new Date());
        product.setModifiedDate(new Date());
        product.setStatus(ProductStatus.ACTIVE);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product productExisting = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.ResourceNotFoundException", Product.class.getSimpleName()));
        Product dataUpdate = productRequestDTOMapper.toEntity(productRequestDTO);
        //Kiểm tra file ảnh
        if (productRequestDTO.getImgFile() != null) {
            if (!productRequestDTO.getImgFile().isEmpty()) {
                handleUpLoadFile(productExisting, productRequestDTO.getImgFile(), FileUpLoadUtil.FILE_TYPE_IMAGE, 10L);
            }
        }
        //Cập nhật  các trường dữ liệu
        setDataUpdate(productExisting, dataUpdate);
        // Case 1: Nếu không truyền danh sách categoryId, set tất cả về INACTIVE
        if (productRequestDTO.getCategoryIds() != null) {
            List<ProductCategory> productCategoryList = productExisting.getProductCategories();
            //Case 2 người dùng có truyền id đối với những bản ghi đã tồn tại thì set là ACTIVE còn bản ghi chưa tồn tại thì Thêm  mới
            //Xử lý cập nhật trạng thái cho các bản ghi đã tồn tại
            if (productCategoryList != null) {
                productCategoryList.forEach(productCategory -> {
                    Long currentCategoryId = productCategory.getCategory().getId();
                    //Nếu categoryId có trong danh sách truyền vào set ACTIVE
                    if (productRequestDTO.getCategoryIds().contains(currentCategoryId)) {
                        productCategory.setStatus(ProductCategoryStatus.ACTIVE);
                        productRequestDTO.getCategoryIds().removeIf(aLong -> aLong == currentCategoryId);
                    } else {
                        //Nếu không có trong danh sách set INACTIVE
                        productCategory.setStatus(ProductCategoryStatus.INACTIVE);
                    }
                });
                //Thêm mới các bản ghi chưa tồn tại
                if(productRequestDTO.getCategoryIds().size() > 0 ){
                    List<Category> categoryList = categoryRepository.findAllById(productRequestDTO.getCategoryIds());
                    for (Category category: categoryList) {
                        ProductCategory productCategory = ProductCategory
                                .builder()
                                .product(productExisting)
                                .category(category)
                                .createdBy("ADMIN-NTP")
                                .modifiedBy("ADMIN-NTP")
                                .createdDate(new Date())
                                .modifiedDate(new Date())
                                .status(ProductCategoryStatus.ACTIVE)
                                .build();
                        productCategoryList.add(productCategory);
                    }
                }
            }
        } else {
            productExisting.getProductCategories().stream().forEach(productCategory -> productCategory.setStatus(ProductCategoryStatus.INACTIVE));
        }
        // Save
        Product productUpdated = productRepository.save(productExisting);
        return productResponseDTOMapper.toDTO(productUpdated);
    }


    @Override
    @Transactional
    public void softDelete(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.ResourceNotFoundException", Product.class.getSimpleName()));
        productRepository.softDelete(id);
    }

    @Override
    public Page<ProductSearchResponseDTO> searchProduct(PaginationObject paginationObject, String productCode, String name, Date startDate, Date endDate, Long categoryId) {
        return productRepository.searchPage(paginationObject.toPageable(), productCode, name, startDate, FormatDateUtil.setEndDate(endDate), categoryId).map(productSearchResponseDTOMapper::toDTO);
    }

    void setDataUpdate(Product productExisting, Product dataUpdate) {
        productExisting.setModifiedDate(new Date());
        productExisting.setModifiedBy("ADMIN-NTP");
        productExisting.setName(dataUpdate.getName());
        productExisting.setDescription(dataUpdate.getDescription());
        productExisting.setPrice(dataUpdate.getPrice());
        productExisting.setQuantity(dataUpdate.getQuantity());
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
