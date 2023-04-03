package com.fc.final7.domain.product.repository.datajpa;

import com.fc.final7.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = "select p from Product p" +
            " join fetch p.productPeriods pp" +
            " join fetch p.productContents pc" +
            " join fetch p.options po" +
            " where p.id = :productId")
    Optional<Product> findProductFetchJoinById(@Param("productId") Long productId);

    @Query(value = "select distinct p from Product p" +
            " join fetch p.categories pc" +
            " where p.title like %:keyWord%" +
            " or p.description like %:keyWord%")
    List<Product> searchProduct(@Param("keyWord") String keyWord);
}
