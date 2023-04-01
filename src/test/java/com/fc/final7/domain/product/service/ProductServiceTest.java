package com.fc.final7.domain.product.service;

import com.fc.final7.domain.product.entity.*;
import com.fc.final7.domain.product.repository.datajpa.CategoryRepository;
import com.fc.final7.domain.product.repository.datajpa.ProductContentRepository;
import com.fc.final7.domain.product.repository.datajpa.ProductPeriodRepository;
import com.fc.final7.domain.product.repository.datajpa.ProductRepository;
import com.fc.final7.domain.product.repository.datajpa.ProductOptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;

import static com.fc.final7.domain.product.entity.SalesStatus.OPEN;
import static com.fc.final7.global.entity.ContentType.IMAGE;
import static com.fc.final7.global.entity.ContentType.TEXT;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductContentRepository productContentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductPeriodRepository productPeriodRepository;
    @Autowired
    ProductOptionRepository productOptionRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    public void 더미_데이터_넣는_테스트() throws Exception {
        Product product1 = Product.builder()
                .description("5070 누구나 참가하는 중앙아시아 3국 일주 여행&\n" +
                        "\n" +
                        "3국 중 가장 아름다운 키르키즈스탄 특화 여행상품&\n" +
                        "\n" +
                        "힘들지 않는 가벼운 트레킹 및 하이킹 포함 일정\n" +
                        "\n")
                .feature("포함투어 20개(타사상품 비교必)/No팁/No쇼핑/No옵션")
                .flight("인천-알마티In/타쉬켄트Out/중앙아시아 내 항공 이동 2회")
                .price(5790000L)
                .region("카자흐스탄/키르키즈스탄/우즈베키스탄")
                .thumbnail("https://cdn.imweb.me/thumbnail/20220330/ea0dbb6095678.png")
                .title("중앙아시아 3국 15일 ")
                .salesStatus(OPEN)
                .term(15)
                .build();
        productRepository.save(product1);

        Category category1 = new Category("groups", "5070끼리,누구든지", null, product1);
        Category category2 = new Category("themes", "문화탐방,트레킹", null, product1);
        Category category3 = new Category("region", "인도,중앙아시아", "중앙아시아", product1);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        ProductContent productContent1 = new ProductContent(1L, IMAGE, "https://cdn.imweb.me/upload/S202107158604372051740/6b4734bc55027.jpg", product1);
        ProductContent productContent2 = new ProductContent(2L, TEXT, "상세일정 어쩌구 저쩌구", product1);
        productContentRepository.save(productContent1);
        productContentRepository.save(productContent2);

        String startDate1 = "2023-05-30";
        String endDate1 = "2023-06-13";
        String startDate2 = "2023-06-13";
        String endDate2 = "2023-06-27";

        ProductPeriod productPeriod1 = new ProductPeriod(startDate1, endDate1, OPEN, product1);
        ProductPeriod productPeriod2 = new ProductPeriod(startDate2, endDate2, OPEN, product1);

        productPeriodRepository.save(productPeriod1);
        productPeriodRepository.save(productPeriod2);

        ProductOption productOption1 = new ProductOption("room", "싱글차지", 540000L, product1);
        productOptionRepository.save(productOption1);
        //-----------------------상품1-----------------------------//

        Product product2 = Product.builder()
                .description("#스페인 #산티아고 #산티아고순례길 #250Km #까미노 #카미노데산티아고 #생쟝 #피니스테라 #사리아 #피스테라 #피레네산맥 #무시아 #마드리드")
                .feature("800Km 구간 중 엄선된 250Km 도보 여행")
                .flight("이용 항공편에 따라 경유지, 시간 변동됩니다.")
                .price(5290000L)
                .region("스페인 산티아고 순례길")
                .thumbnail("https://cdn.imweb.me/thumbnail/20220804/b2c35e3dc9f22.jpg")
                .title("스페인 산티아고 순례길 하이라이트 17일")
                .salesStatus(OPEN)
                .term(17)
                .build();
        productRepository.save(product2);

        Category category4 = new Category("themes", "문화탐방,성지순례", null, product2);
        Category category5 = new Category("region", "유럽,코카서스", "유럽", product2);
        Category category6 = new Category("groups", "5070끼리", null, product2);

        categoryRepository.save(category4);
        categoryRepository.save(category5);
        categoryRepository.save(category6);

        ProductContent productContent3 = new ProductContent(1L, IMAGE, "https://cdn.imweb.me/upload/S202107158604372051740/1dc85753f7053.jpg", product2);
        ProductContent productContent4 = new ProductContent(2L, TEXT, "상세일정 어쩌구 저쩌구", product2);
        productContentRepository.save(productContent3);
        productContentRepository.save(productContent4);

        ProductPeriod productPeriod3 = new ProductPeriod(startDate1, endDate1, OPEN, product2);
        ProductPeriod productPeriod4 = new ProductPeriod(startDate2, endDate2, OPEN, product2);

        productPeriodRepository.save(productPeriod3);
        productPeriodRepository.save(productPeriod4);

        ProductOption productOption2 = new ProductOption("room", "싱글룸", 1270000L, product2);
        productOptionRepository.save(productOption2);
        //-----------------------상품2-----------------------------//

        Product product3 = Product.builder()
                .description("#스위스 #이지워킹 #트레킹 #리기산 #엥겔베르그 #피르스트 #뮈렌 #라보 #레만호수 #취리히 #루체른 #인터라켄 #베른 #로쟌 #체르마트 #마조레")
                .feature("No팁/No쇼핑")
                .flight("이용 항공편에 따라 경유지, 시간 변동됩니다.")
                .price(4990000L)
                .region("스위스 알프스 트래킹_초급")
                .thumbnail("https://cdn.imweb.me/upload/S202107158604372051740/9c6837e87a2dd.jpg")
                .title("누구나 가능한 이지워킹 스위스 알프스 초급 트레킹 10일")
                .salesStatus(OPEN)
                .term(10)
                .build();
        productRepository.save(product3);

        Category category7 = new Category("themes", "휴양지,문화탐방", null, product3);
        Category category8 = new Category("groups", "5070끼리,누구든지", null, product3);
        Category category9 = new Category("region", "유럽,코카서스", "유럽", product3);

        categoryRepository.save(category7);
        categoryRepository.save(category8);
        categoryRepository.save(category9);

        ProductContent productContent5 = new ProductContent(1L, IMAGE, "https://cdn.imweb.me/upload/S202107158604372051740/13c36d9db3e91.jpg", product3);
        ProductContent productContent6 = new ProductContent(2L, TEXT, "상세일정 어쩌구 저쩌구", product3);
        productContentRepository.save(productContent5);
        productContentRepository.save(productContent6);

        ProductPeriod productPeriod5 = new ProductPeriod(startDate1, endDate1, OPEN, product3);
        ProductPeriod productPeriod6 = new ProductPeriod(startDate2, endDate2, OPEN, product3);

        productPeriodRepository.save(productPeriod5);
        productPeriodRepository.save(productPeriod6);

        ProductOption productOption3 = new ProductOption("room", "싱글룸", 570000L, product3);
        productOptionRepository.save(productOption3);
        //-----------------------상품3-----------------------------//

        Product product4 = Product.builder()
                .description("5070 누구나 참가하는 아이슬란드 오로라여행\n" +
                        "\n" +
                        "아이슬란드 핵심지역 투어와 야간 오로라 헌팅\n" +
                        "\n" +
                        "드라이빙 가이드가 안내하는 7명 정원 소그룹 여행")
                .feature("포함투어 11개(타사상품 비교必)/No팁/No쇼핑/No옵션")
                .flight("항공료 불포함(케플라비크 국제공항 합류 상품)")
                .price(3090000L)
                .region("아이슬란드")
                .thumbnail("https://cdn.imweb.me/thumbnail/20220527/4dd8092691618.png")
                .title("아이슬란드 오로라 7일")
                .salesStatus(OPEN)
                .term(7)
                .build();
        productRepository.save(product4);

        Category category10 = new Category("themes", "문화탐방,휴양지", null, product4);
        Category category11 = new Category("region", "유럽,코카서스", "유럽", product4);
        Category category12 = new Category("groups", "5070끼리,누구든지", null, product4);

        categoryRepository.save(category10);
        categoryRepository.save(category11);
        categoryRepository.save(category12);

        ProductContent productContent7 = new ProductContent(1L, IMAGE, "https://cdn.imweb.me/upload/S202107158604372051740/9d49b58c273fa.jpg", product4);
        ProductContent productContent8 = new ProductContent(2L, TEXT, "상세일정 어쩌구 저쩌구", product4);
        productContentRepository.save(productContent7);
        productContentRepository.save(productContent8);

        ProductPeriod productPeriod7 = new ProductPeriod(startDate1, endDate1, OPEN, product4);
        ProductPeriod productPeriod8 = new ProductPeriod(startDate2, endDate2, OPEN, product4);

        productPeriodRepository.save(productPeriod7);
        productPeriodRepository.save(productPeriod8);

        ProductOption productOption4 = new ProductOption("room", "싱글룸", 900000L, product4);
        productOptionRepository.save(productOption4);
        //-----------------------상품4-----------------------------//

        Product product5 = Product.builder()
                .description("4050 여성들 누구나 참가하는 조지아 일주 여행\n" +
                        "\n" +
                        "코카서스의 백미 조지아를 샅샅히 둘러보는 상품\n" +
                        "\n" +
                        "패키지의 안전함과 자유여행의 즐거움을 동시에~")
                .feature("포함투어 12개(타사상품 비교必)/No팁/No쇼핑/No옵션")
                .flight("인천-트빌리시 왕복 항공")
                .price(5090000L)
                .region("트빌리시/카즈베기/바르지아/보르조미/쿠타이시/메스티아/바투미/고리/우플리시케/시그나기/크바렐리")
                .thumbnail("https://cdn.imweb.me/thumbnail/20220330/fa2942e46240e.png")
                .title("조지아 14일 ")
                .salesStatus(OPEN)
                .term(14)
                .build();
        productRepository.save(product5);

        Category category13 = new Category("groups", "5070끼리,여자끼리", null, product5);
        Category category14 = new Category("themes", "문화탐방,휴양지", null, product5);
        Category category15 = new Category("region", "유럽,코카서스", "코카서스", product5);


        categoryRepository.save(category13);
        categoryRepository.save(category14);
        categoryRepository.save(category15);

        ProductContent productContent9 = new ProductContent(1L, IMAGE, "https://cdn.imweb.me/upload/S202107158604372051740/4cdd35e31964f.jpg", product5);
        ProductContent productContent10 = new ProductContent(2L, TEXT, "상세일정 어쩌구 저쩌구", product5);
        productContentRepository.save(productContent9);
        productContentRepository.save(productContent10);

        ProductPeriod productPeriod9 = new ProductPeriod(startDate1, endDate1, OPEN, product5);
        ProductPeriod productPeriod10 = new ProductPeriod(startDate2, endDate2, OPEN, product5);

        productPeriodRepository.save(productPeriod9);
        productPeriodRepository.save(productPeriod10);

        ProductOption productOption5 = new ProductOption("room", "싱글차지", 440000L, product5);
        productOptionRepository.save(productOption5);
        //-----------------------상품5-----------------------------//

    }

}