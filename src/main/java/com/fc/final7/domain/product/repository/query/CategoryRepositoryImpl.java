package com.fc.final7.domain.product.repository.query;


import com.fc.final7.domain.product.dto.SearchConditionDTO;
import com.fc.final7.domain.product.dto.SearchConditionListDTO;
import com.fc.final7.domain.product.entity.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.fc.final7.domain.product.entity.QCategory.category;
import static com.fc.final7.domain.product.entity.QProduct.product;
import static com.fc.final7.domain.product.entity.SalesStatus.OPEN;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 카테고리 동적 조회 메서드(페이징 처리 한페이지당 12개씩 반환하는거로)
     */
    @Override
    public Page<Product> groupByCategorySearch(SearchConditionListDTO searchConditionListDTO, PageRequest pageRequest) {
        BooleanBuilder builder = new BooleanBuilder();

        for (SearchConditionDTO searchConditionDTO : searchConditionListDTO.getSearchConditions()) {
            String mainCategory = searchConditionDTO.getMainCategory();
            String middleCategory = searchConditionDTO.getMiddleCategory();

            BooleanBuilder categoryBuilder = new BooleanBuilder();
            if (mainCategory != null) {
                categoryBuilder.and(category.mainCategory.eq(mainCategory));
            }
            if (middleCategory != null) {
                categoryBuilder.and(category.middleCategory.contains(middleCategory));
            }
            builder.and(product.categories.any().in(JPAExpressions
                    .selectFrom(category)
                    .where(categoryBuilder)
            ));
        }

        List<Product> content = queryFactory
                .selectFrom(product)
                .where(builder.and(product.salesStatus.eq(OPEN)))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(product.count())
                .from(product)
                .where(builder.and(product.salesStatus.eq(OPEN)))
                .fetchOne();

        return new PageImpl<>(content, pageRequest, count);
    }

}
