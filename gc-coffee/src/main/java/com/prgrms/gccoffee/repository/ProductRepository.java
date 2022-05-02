package com.prgrms.gccoffee.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prgrms.gccoffee.model.Category;
import com.prgrms.gccoffee.model.Product;

public interface ProductRepository {

	List<Product> findAll();

	Product insert(Product product);

	Product update(Product product);

	Optional<Product> findById(UUID productId);

	Optional<Product> findByName(String productName);

	List<Product> findByCategory(Category category);

	void deleteAll();
}
