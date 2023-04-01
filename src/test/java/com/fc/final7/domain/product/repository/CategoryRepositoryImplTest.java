package com.fc.final7.domain.product.repository;

import com.fc.final7.domain.product.dto.response.ProductResponseDTO;
import com.fc.final7.domain.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static com.fc.final7.domain.product.entity.QCategory.category;
import static com.fc.final7.domain.product.entity.QProduct.product;

@SpringBootTest
class CategoryRepositoryImplTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void queryDslTest() throws Exception {
        List<Product> products = queryFactory
                .select(product)
                .from(product)
                .where(product.id.in(
                        queryFactory.select(category.id)
                                .from(category)
                                .where(category.mainCategory.eq("groups"))
                                .fetch()
                ))
                .fetch();
        List<ProductResponseDTO> productResponseDTOS = products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());

        for (ProductResponseDTO productResponseDTO : productResponseDTOS) {
            System.out.println("productDTO = " + productResponseDTO);
        }

    }
}