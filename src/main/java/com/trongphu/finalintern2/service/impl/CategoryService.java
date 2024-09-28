package com.trongphu.finalintern2.service.impl;

import com.trongphu.finalintern2.dto.category.request.CategoryRequestDTO;
import com.trongphu.finalintern2.dto.category.response.CategoryResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.enumutil.CategoryStatus;
import com.trongphu.finalintern2.enumutil.ProductCategoryStatus;
import com.trongphu.finalintern2.exception.DuplicateCodeEntityException;
import com.trongphu.finalintern2.exception.ResourceNotFoundException;
import com.trongphu.finalintern2.exception.file.FileUploadErrorException;
import com.trongphu.finalintern2.mapper.category.request.CategoryRequestDTOMapper;
import com.trongphu.finalintern2.mapper.category.response.CategoryResponseDTOMapper;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import com.trongphu.finalintern2.repository.CategoryRepository;
import com.trongphu.finalintern2.service.interfaceservice.ICategoryService;
import com.trongphu.finalintern2.util.file.FileUpLoadUtil;
import com.trongphu.finalintern2.util.formater.FormatDateUtil;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Trong Phu on 17/09/2024 23:00
 *
 * @author Trong Phu
 */
@Service(value = "CategoryService")
public class CategoryService implements ICategoryService {
    //Repo
    private final CategoryRepository categoryRepository;

    //Mapper
    private final CategoryResponseDTOMapper categoryResponseDTOMapper;
    private final CategoryRequestDTOMapper categoryRequestDTOMapper;

    //Constructor
    public CategoryService(
            CategoryRepository categoryRepository,
            CategoryResponseDTOMapper categoryResponseDTOMapper,
            CategoryRequestDTOMapper categoryRequestDTOMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryResponseDTOMapper = categoryResponseDTOMapper;
        this.categoryRequestDTOMapper = categoryRequestDTOMapper;
    }

    //Get all
    @Override
    public List<CategoryResponseDTO> getAll() {
        return categoryResponseDTOMapper.toListDTO(categoryRepository.findAll());
    }

    // Get By id
    @Override
    public CategoryResponseDTO getById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("exception.ResourceNotFoundException", Category.class.getSimpleName());
        }
        return categoryResponseDTOMapper.toDTO(category.get());
    }

    //Get by code
    @Override
    public CategoryResponseDTO getByCode(String code) {
        return null;
    }

    // Create new
    @Override
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        if (checkCode(categoryRequestDTO.getCategoryCode())) {
            Category categoryNew = categoryRequestDTOMapper.toEntity(categoryRequestDTO);
            categoryNew.setCreatedDate(new Date());
            categoryNew.setModifiedDate(new Date());
            categoryNew.setCreatedBy("ADMIN-NTP");
            categoryNew.setModifiedBy("ADMIN-NTP");
            categoryNew.setStatus(CategoryStatus.ACTIVE);
            handleUploadFile(categoryNew, categoryRequestDTO.getImgFile(), FileUpLoadUtil.FILE_TYPE_IMAGE, 2L);
            return categoryResponseDTOMapper.toDTO(categoryRepository.save(categoryNew));
        }
        return null;
    }

    // Update category
    @Override
    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category categoryExisting = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.ResourceNotFoundException", Category.class.getSimpleName()));
        Category categoryToUpdate = categoryRequestDTOMapper.toEntity(categoryRequestDTO);

        //Cập nhật các trường cần thay đổi
        categoryExisting.setModifiedDate(new Date());
        categoryExisting.setModifiedBy("ADMIN-NTP");
        categoryExisting.setName(categoryToUpdate.getName());
        categoryExisting.setDescription(categoryToUpdate.getDescription());

        //Kiểm tra nếu có file ảnh đính kèm thì update lại tên ảnh
        if (categoryRequestDTO.getImgFile() != null) {
            if(!categoryRequestDTO.getImgFile().isEmpty()){
                handleUploadFile(categoryExisting, categoryRequestDTO.getImgFile(), FileUpLoadUtil.FILE_TYPE_IMAGE, 2L);
            }
        }
        // Lưu đối tượng cần cập nhật vào DB
        Category categoryUpdated = categoryRepository.save(categoryExisting);
        return categoryResponseDTOMapper.toDTO(categoryUpdated);
    }

    // Xóa mềm
    @Override
    @Transactional
    public void softDelete(Long id) {
        Category categoryD = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.ResourceNotFoundException", Category.class.getSimpleName()));
        categoryD.getProductCategories().stream().forEach(productCategory -> {productCategory.setStatus(ProductCategoryStatus.INACTIVE);});
        categoryRepository.softDelete(id);
    }

    //Phân trang tìm kiếm
    @Override
    public Page<CategoryResponseDTO> searchPage(PaginationObject paginationObject, String categoryCode, String name, Date startDate, Date endDate) {
        return categoryRepository
                .searchPage(paginationObject.toPageable(), categoryCode, name, startDate, FormatDateUtil.setEndDate(endDate))
                .map(categoryResponseDTOMapper::toDTO);
    }

    /**
     * Hàm chức năng check code tồn tại
     */
    private boolean checkCode(String code) {
        Category category = categoryRepository.findCategoryByCategoryCode(code);
        if (category != null) {
            throw new DuplicateCodeEntityException("exception.DuplicateCodeEntityException", code);
        }
        return true;
    }

    void handleUploadFile(Category category, MultipartFile file, String conditionType, Long conditionSize) {
        try {
            category.setImg(FileUpLoadUtil.uploadFile(file, conditionType, conditionSize));
        } catch (IOException e) {
            throw new FileUploadErrorException("exception_file.FileUploadErrorException");
        }
    }
}
