package com.trongphu.finalintern2.repository;

import com.trongphu.finalintern2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

}
