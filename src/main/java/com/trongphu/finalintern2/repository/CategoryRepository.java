package com.trongphu.finalintern2.repository;

import com.trongphu.finalintern2.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Trong Phu on 17/09/2024 22:47
 *
 * @author Trong Phu
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category  c WHERE c.categoryCode = :code")
    Category findCategoryByCategoryCode(@Param(value = "code") String code);

    @Query("""
             SELECT c FROM Category c
             WHERE c.status = 'ACTIVE'
             AND (:categoryCode IS NULL OR c.categoryCode LIKE %:categoryCode%)
             AND (:name IS NULL OR c.name LIKE %:name%)
             AND (:startDate IS NULL OR c.createdDate >= :startDate)
             AND (:endDate IS NULL OR c.createdDate <= :endDate)
            """)
    Page<Category> searchPage(
            Pageable pageable,
            @Param("categoryCode") String categoryCode,
            @Param("name") String name,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("""
                SELECT c FROM Category c WHERE c.status like 'ACTIVE' AND c.id = :id
            """)
    Optional<Category> findById(@Param("id") Long id);


}
