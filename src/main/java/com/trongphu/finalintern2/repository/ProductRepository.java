package com.trongphu.finalintern2.repository;

import com.trongphu.finalintern2.entity.Category;
import com.trongphu.finalintern2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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
    @EntityGraph(attributePaths = {"productCategories", "productCategories.category"})
    @Override
    @Query(value = """
                SELECT p FROM Product p WHERE p.status = 'ACTIVE'
            """)
    List<Product> findAll();

    @Query("SELECT p FROM Product  p WHERE p.productCode = :code AND p.status = 'ACTIVE'")
    Optional<Product> findCategoryByProductCode(@Param(value = "code") String code);

    @Override
    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.productCategories ppc LEFT JOIN FETCH ppc.category ppcc WHERE p.id  = :id AND p.status = 'ACTIVE'")
    Optional<Product> findById(@Param(value = "id") Long id);

//    @EntityGraph(attributePaths = {"productCategories", "productCategories.category"})
    @Query("""
                SELECT DISTINCT p FROM Product p 
                LEFT JOIN  p.productCategories ppc
                LEFT JOIN  ppc.category c
                WHERE
                p.status = 'ACTIVE'
                AND
                (:productCode IS NULL OR p.productCode LIKE %:productCode%)
                AND
                (:name IS NULL OR p.name LIKE %:name%)  
                AND 
                (:startDate IS NULL OR p.createdDate >= :startDate)
                AND 
                (:endDate IS NULL OR p.createdDate <= :endDate)
                AND 
                (:categoryId IS NULL OR c.id = :categoryId AND ppc.status = 'ACTIVE')  ORDER BY p.id DESC 
            """)

    Page<Product> searchPage(
            Pageable pageable,
            @Param(value = "productCode") String productCode,
            @Param(value = "name") String name,
            @Param(value = "startDate") Date startDate,
            @Param(value = "endDate") Date endDate,
            @Param(value = "categoryId") Long categoryId
    );



    @Query("""
        SELECT p FROM Product p 
        LEFT JOIN FETCH p.productCategories pc
        LEFT JOIN FETCH pc.category c
        WHERE p.id IN :productIds
    """)
    List<Product> findProductsWithCategoriesByIds(
            @Param("productIds") List<Long> productIds
    );



    @Modifying
    @Query("UPDATE Product p SET p.status = 'INACTIVE' WHERE p.id = :id")
    void softDelete(@Param(value = "id") Long id);
}
