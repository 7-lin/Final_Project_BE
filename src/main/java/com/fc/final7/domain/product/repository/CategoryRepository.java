package com.fc.final7.domain.product.repository;

import com.fc.final7.domain.product.entity.Category;
import com.fc.final7.domain.product.repository.query.CategoryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> , CategoryQueryRepository {
}
