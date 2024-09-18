package com.trongphu.finalintern2.service.impl;

import com.trongphu.finalintern2.dto.category.request.CategoryRequestDTO;
import com.trongphu.finalintern2.dto.category.response.CategoryResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.enumutil.CategoryStatus;
import com.trongphu.finalintern2.exception.DuplicateCodeEntityException;
import com.trongphu.finalintern2.exception.ResourceNotFoundException;
import com.trongphu.finalintern2.exception.file.FileUploadErrorException;
import com.trongphu.finalintern2.mapper.category.request.CategoryRequestDTOMapper;
import com.trongphu.finalintern2.mapper.category.response.CategoryResponseDTOMapper;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import com.trongphu.finalintern2.repository.CategoryRepository;
import com.trongphu.finalintern2.service.interfaceservice.ICategoryService;
import com.trongphu.finalintern2.util.file.FileUpLoadUtil;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import java.io.IOException;
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
        if(category.isEmpty()){
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
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        if (checkCode(categoryRequestDTO.getCategoryCode())) {
            Category categoryNew = categoryRequestDTOMapper.toEntity(categoryRequestDTO);
            categoryNew.setCreatedDate(new Date());
            categoryNew.setModifiedDate(new Date());
            categoryNew.setCreatedBy("ADMIN-NTP");
            categoryNew.setModifiedBy("ADMIN-NTP");
            categoryNew.setStatus(CategoryStatus.ACTIVE);
            try {
                boolean checkFile = FileUpLoadUtil.checkFileUpload(categoryRequestDTO.getImgFile(), FileUpLoadUtil.FILE_TYPE_IMAGE, 10L);
                if (checkFile) {
                    categoryNew.setImg(FileUpLoadUtil.uploadFile(categoryRequestDTO.getImgFile()));
                }
            } catch (IOException e) {
                throw new FileUploadErrorException("exception_file.FileUploadErrorException");
            }
            return categoryResponseDTOMapper.toDTO(categoryRepository.save(categoryNew));
        }
        return null;
    }

    // Update
    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) {
        return null;
    }

    //Phân trang tìm kiếm
    @Override
    public Page<CategoryResponseDTO> searchPage(PaginationObject paginationObject, String categoryCode, String name, Date startDate, Date endDate) {
        return   categoryRepository
                .searchPage(paginationObject.toPageable(), categoryCode, name, startDate, endDate)
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
}
