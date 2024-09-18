package com.trongphu.finalintern2.repository;

import com.trongphu.finalintern2.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Trong Phu on 18/09/2024 00:29
 *
 * @author Trong Phu
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
