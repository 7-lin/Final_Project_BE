package com.fc.final7.domain.product.repository.datajpa;

import com.fc.final7.domain.product.entity.Category;
import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.product.repository.query.CategoryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> , CategoryQueryRepository {

    List<Category> findAllByProduct(Product productId);

}
