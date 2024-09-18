package com.trongphu.finalintern2.service.interfaceservice;

import com.trongphu.finalintern2.dto.category.request.CategoryRequestDTO;
import com.trongphu.finalintern2.dto.category.response.CategoryResponseDTO;
import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.objectutil.PaginationObject;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * Created by Trong Phu on 17/09/2024 23:00
 *
 * @author Trong Phu
 */
public interface ICategoryService extends BaseService<Category, Long, CategoryRequestDTO, CategoryResponseDTO>{
    /**
     * Phân trang tìm kiếm {@link Category}
     * @param paginationObject đối tượng {@link PaginationObject} hỗ trợ phân trang
     * @param categoryCode tìm kiếm theo trường categoryCode
     * @param name tìm kiếm theo trường name
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * */
    Page<CategoryResponseDTO> searchPage(PaginationObject paginationObject, String categoryCode, String name, Date startDate, Date endDate);
}
