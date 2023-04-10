package com.fc.final7.domain.product.repository.query;


import com.fc.final7.domain.product.dto.request.CategoryConditionDTO;
import com.fc.final7.domain.product.dto.request.FilteringDTO;
import com.fc.final7.domain.product.entity.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.fc.final7.domain.product.entity.QCategory.category;
import static com.fc.final7.domain.product.entity.QProduct.product;
import static com.fc.final7.domain.product.entity.SalesStatus.OPEN;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 카테고리 동적 조회 메서드(페이징 처리 한페이지당 12개씩 반환하는거로)
     */
    @Override
    public Page<Product> groupByCategorySearch(FilteringDTO filteringDTO, Pageable pageable) {
        BooleanBuilder builder = getCategoryBuilder(filteringDTO);

        List<Product> content = queryFactory
                .selectFrom(product)
                .orderBy(sortCondition(filteringDTO.getSort()))
                .where(builder,
                        filteringPrice(filteringDTO.getMinPrice(), filteringDTO.getMaxPrice()),
                        filteringPeriod(filteringDTO.getMinPeriod(), filteringDTO.getMaxPeriod()),
                        (product.salesStatus.eq(OPEN)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = getCount(filteringDTO, builder);

        return new PageImpl<>(content, pageable, count);
    }


    //카테고리 검색조건에 따라 동적으로 바뀌는 builder 생성
    public BooleanBuilder getCategoryBuilder(FilteringDTO filteringDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        for (CategoryConditionDTO categories : filteringDTO.getCategories()) {
            String mainCategory = categories.getMainCategory();
            String middleCategory = categories.getMiddleCategory();

            BooleanBuilder categoryBuilder = new BooleanBuilder();
            if (mainCategory != null) {
                categoryBuilder.and(category.mainCategory.eq(mainCategory));
            }
            if (middleCategory != null) {
                categoryBuilder.and(category.middleCategory.eq(middleCategory));
            }
            builder.and(product.categories.any().in(JPAExpressions
                    .selectFrom(category)
                    .where(categoryBuilder)
            ));
        }
        return builder;
    }

    //페이징을 하려면 따로 카운트 쿼리를 날려줘야 함
    public Long getCount(FilteringDTO filteringDTO, BooleanBuilder builder) {
        return queryFactory
                .select(product.count())
                .from(product)
                .where(builder,
                        filteringPrice(filteringDTO.getMinPrice(), filteringDTO.getMaxPrice()),
                        filteringPeriod(filteringDTO.getMinPeriod(), filteringDTO.getMaxPeriod()),
                        (product.salesStatus.eq(OPEN)))
                .fetchOne();
    }

    //매개변수로 넘어온 가격의 범위가 null인지 아닌지 판단한 후 해당 가격 사이의 조건을 건다
    public BooleanBuilder filteringPrice(Integer minPrice, Integer maxPrice) {
        BooleanBuilder builder = new BooleanBuilder();
        if(minPrice != null && maxPrice !=null) {
            builder.and(product.price.between(minPrice, maxPrice));
        }
        return builder;
    }

    //매개변수로 넘어온 기간의 범위가 null인지 아닌지 판단한 후 해당 기간 사이의 조건을 건다.
    public BooleanBuilder filteringPeriod(Integer minPeriod, Integer maxPeriod) {
        BooleanBuilder builder = new BooleanBuilder();
        if(minPeriod != null && maxPeriod != null) {
            builder.and(product.term.between(minPeriod, maxPeriod));
        }
        return builder;
    }

    //정렬 조건이 null이 넘어오면 productId 기준으로 오름차순 하고
    //그 외의 조건들이 들어오면 해당 분기를 거쳐 적당한 정렬을 반환한다.
    public OrderSpecifier sortCondition(String sort) {
        if(!StringUtils.hasText(sort)) {
            return new OrderSpecifier(Order.ASC, product.id);
        }else {
            if(sort.equals("priceAsc")) {
                return new OrderSpecifier(Order.ASC, product.price);
            } else if (sort.equals("priceDesc")) {
                return new OrderSpecifier(Order.DESC, product.price);
            } else if (sort.equals("periodAsc")) {
                return new OrderSpecifier(Order.ASC, product.term);
            } else {
                return new OrderSpecifier(Order.DESC, product.term);
            }
        }
    }

}
