package com.prgrms.gccoffee.repository;

import static com.prgrms.gccoffee.commons.JdbcUtils.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrms.gccoffee.model.Category;
import com.prgrms.gccoffee.model.Product;

@Repository
public class ProductJdbcRepositoryImpl implements ProductRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ProductJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Product> findAll() {
		return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
	}

	@Override
	public Product insert(Product product) {
		int update = jdbcTemplate.update(
			"INSERT INTO products(product_id, productName, category, price, description, created_at, updated_at)" +
				" VALUES(UUID_TO_BIN(:productId), :productName, :category, :price, :description, :created_at, :updated_at )",
			toParamMap(product));
		if (update != 1) {
			throw new RuntimeException("Nothing was inserted");
		}

		return product;
	}

	@Override
	public Product update(Product product) {
		return null;
	}

	@Override
	public Optional<Product> findById(UUID productId) {
		return Optional.empty();
	}

	@Override
	public Optional<Product> findByName(String productName) {
		return Optional.empty();
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return null;
	}

	@Override
	public void deleteAll() {

	}

	private static final RowMapper<Product> productRowMapper = (rs, i) -> {
		UUID productId = toUUID(rs.getBytes("product_id"));
		String productName = rs.getString("productName");
		Category category = Category.valueOf(rs.getString("category"));
		long price = rs.getLong("price");
		String description = rs.getString("description");
		LocalDateTime createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
		LocalDateTime updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));

		return new Product(productId,
			productName,
			category,
			price,
			description,
			createdAt,
			updatedAt);
	};

	private Map<String, Object> toParamMap(Product product) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", product.getProductId().toString().getBytes());
		paramMap.put("productName", product.getProductName());
		paramMap.put("category", product.getCategory().toString());
		paramMap.put("price", product.getPrice());
		paramMap.put("description", product.getDescription());
		paramMap.put("created_at", product.getCreatedAt());
		paramMap.put("updated_at", product.getUpdatedAt());
		return paramMap;
	}

}
