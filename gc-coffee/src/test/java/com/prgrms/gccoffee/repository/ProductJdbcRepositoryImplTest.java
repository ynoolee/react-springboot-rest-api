package com.prgrms.gccoffee.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.prgrms.gccoffee.model.Category;
import com.prgrms.gccoffee.model.Product;

@SpringBootTest
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProductJdbcRepositoryImplTest {

	Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

	@Autowired
	ProductRepository repository;

	@BeforeEach
	public void setup() {
		repository.insert(newProduct);
	}

	@Test
	// @Order(1)
	@DisplayName("상품을 추가 할 수 있다")
	public void testInsert() {
		List<Product> all = repository.findAll();
		assertThat(all.isEmpty(), is(false));
	}

	@Test
	// @Order(2)
	@DisplayName("상품을 이름으로 조회할 수 있다")
	public void testFindByName() {
		Optional<Product> product = repository.findByName(newProduct.getProductName());

		assertThat(product.isEmpty(), is(false));
	}

	@Test
	// @Order(3)
	@DisplayName("상품을 Id 로 조회할 수 있다")
	public void testFindById() {
		Optional<Product> product = repository.findById(newProduct.getProductId());

		assertThat(product.isEmpty(), is(false));
	}

	@Test
	// @Order(4)
	@DisplayName("상품들을 카테고리로 조회할 수 있다")
	public void testFindByCategory() {
		List<Product> products = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);

		assertThat(products.isEmpty(), is(false));
	}

	@Test
	@DisplayName("상품을 전체 삭제한다")
	void testDeleteAll() {
		repository.deleteAll();
		List<Product> all = repository.findAll();

		assertThat(all.isEmpty(), is(true));
	}

	@Test
	@DisplayName("상품 수정 가능")
	void testUpdate() {
		newProduct.setProductName("updated-product");
		repository.update(newProduct);

		Optional<Product> product = repository.findById(newProduct.getProductId());
		assertThat(product.isEmpty(), is(false));
		assertThat(product.get(), samePropertyValuesAs(newProduct));
	}
}