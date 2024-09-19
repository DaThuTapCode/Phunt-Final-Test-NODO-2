package com.trongphu.finalintern2.repository;

import com.trongphu.finalintern2.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

/**
 * Created by Trong Phu on 18/09/2024 00:29
 *
 * @author Trong Phu
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
//    @Modifying
//    @Query("UPDATE ProductCategory pc SET pc.status = 'INACTIVE' WHERE pc.product.id = :idProduct")
//    void softDeleteByProduct(@Param(value = "idProduct") Long idProduct);
}
