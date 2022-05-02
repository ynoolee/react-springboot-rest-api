package com.prgrms.gccoffee.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.prgrms.gccoffee.model.Category;
import com.prgrms.gccoffee.model.Product;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductJdbcRepositoryImplTest {

	Product product = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);
	@TestConfiguration
	static class Config {
		@Bean
		public DataSource dataSource() {
			HikariDataSource dataSource = DataSourceBuilder.create()
				.url("jdbc:mysql://localhost/test_order_mgmt")
				.username("yeon")
				.password("root1234")
				.type(HikariDataSource.class) // 이렇게 실제 사용할 데이터소스 구현체를 지정해줄수도 있다
				.build();
			dataSource.setMaximumPoolSize(1000);
			dataSource.setMinimumIdle(100);

			return dataSource;
		}

		@Bean
		public JdbcTemplate jdbcTemplate(DataSource dataSource){
			return new JdbcTemplate(dataSource);
		}
	}
	// static EmbeddedMysql embeddedMysql;
	// @BeforeAll
	// static void setup() {
	// 	MysqldConfig config = aMysqldConfig(Version.v8_0_11)
	// 		.withCharset(Charset.UTF8)
	// 		.withPort(2215)
	// 		.withUser("test", "test1234!")
	// 		.withTimeZone("Asia/Seoul")
	// 		.build();
	// 	embeddedMysql = anEmbeddedMysql(config)
	// 		.addSchema("test-order_mgmt", ScriptResolver.classPathScripts("schema.sql"))
	// 		.start();
	//
	// }
	// @AfterAll
	// static void cleanup() {
	// 	embeddedMysql.stop();
	// }

	@Autowired
	ProductRepository repository;

	@Test
	@Order(1)
	@DisplayName("상품을 추가 할 수 있다")
	void testInsert() {
		repository.insert(product);
		List<Product> all = repository.findAll();
		assertThat(all.isEmpty(), is(false));
	}

}