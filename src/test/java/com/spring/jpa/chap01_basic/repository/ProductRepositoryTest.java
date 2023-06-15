package com.spring.jpa.chap01_basic.repository;

import com.spring.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.jpa.chap01_basic.entity.Product.Category.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach //테스트 돌리기 전에 실행
    void insertDummyData(){
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(1200000)
                .build();
        Product p2 = Product.builder()
                .name("볶음밥")
                .category(FOOD)
                .price(8000)
                .build();
        Product p3 = Product.builder()
                .name("정장")
                .category(FASHION)
                .price(200000)
                .build();
        Product p4 = Product.builder()
                .name("찌개")
                .category(FOOD)
                .price(5000)
                .build();


        //when

        Product saved = productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);

        assertNotNull(saved);
    }


    @Test
    @DisplayName("5번째 상품을 데이터베이스에 저장")
    void testSave() {
        //given
    Product product = Product.builder()
            .name("구두")
            .price(210000)
            .category(FASHION)
            .build();
        Product save = productRepository.save(product);
        assertNotNull(save);
        //then

    }
    @Test
    @DisplayName("데이터 삭제")
    void testRemove() {
        //given
        long id = 2L;
        //when
        productRepository.deleteById(id);
        productRepository.findAll();
        //then
    }
    @Test
    @DisplayName("상품 전체조회를 하면 상품의 개수가 4개여야 한다.")
    void testFindAll() {
        //given

        //when
        List<Product> productList = productRepository.findAll();
        productList.forEach(System.out::println);
        assertEquals(4,productList.size());

        //then
    }
    @Test
    @DisplayName("3번 상품을 조회하면 상품명이 '정장'이여야 한다.")
    void testFindOne() {
        //given
        long id = 3L;
        //when
        Optional<Product> product = productRepository.findById(id);

        //then
        product.ifPresent(p -> {
            assertEquals("정장",p.getName());
        });
    Product product1 = product.get();
    assertNotNull(product1);
    }
}