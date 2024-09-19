package com.trongphu.finalintern2.repository;

import com.trongphu.finalintern2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Trong Phu on 17/09/2024 22:46
 *
 * @author Trong Phu
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    @Query(value = """
                SELECT p FROM Product p LEFT JOIN FETCH p.productCategories
            """)
    List<Product> findAll();

    @Override
    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.productCategories WHERE p.id  = :id AND p.status = 'ACTIVE'")
    Optional<Product> findById(@Param(value = "id") Long id);

    @Query("""
            SELECT p FROM Product p LEFT JOIN FETCH p.productCategories 
            WHERE 
            p.status = 'ACTIVE'
            AND 
            (:productCode IS NULL )
            AND 
            (:name IS NULL ) 
            """)
    Page<Product> searchPage(
            @Param(value = "productCode") String productCode,
            @Param(value = "name") String name,
            @Param(value = "startDate") Date startDate,
            @Param(value = "endDate") Date endDate
    );

    @Modifying
    @Query("UPDATE Product p SET p.status = 'INACTIVE' WHERE p.id = :id")
    void softDelete(@Param(value = "id") Long id);
}
