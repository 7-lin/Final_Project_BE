package com.fc.final7.domain.product.repository.datajpa;

import com.fc.final7.domain.product.entity.Product;
import com.fc.final7.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    List<ProductOption> findAllByProduct(Product product);
}
