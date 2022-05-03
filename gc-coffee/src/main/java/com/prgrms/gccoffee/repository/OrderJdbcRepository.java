package com.prgrms.gccoffee.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.gccoffee.model.Order;
import com.prgrms.gccoffee.model.OrderItem;

@Repository
public class OrderJdbcRepository implements OrderRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public Order insert(Order order) {
		jdbcTemplate.update(
			"INSERT INTO orders(order_id, email, address, postcode, order_status, created_at,updated_at) " +
				"VALUES(UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt,:updatedAt)",
			toOrderedParamMap(order));
		order.getOrderItems()
			.forEach(item -> jdbcTemplate.update(
				"INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) "
					+
					"VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
				toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item)));

		return order;
	}

	private Map<String, Object> toOrderedParamMap(Order order) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", order.getOrderId().toString().getBytes());
		paramMap.put("email", order.getEmail().getEmail());
		paramMap.put("address", order.getAddress());
		paramMap.put("postcode", order.getPostcode());
		paramMap.put("orderStatus", order.getOrderStatus().toString());
		paramMap.put("createdAt", order.getCreatedAt());
		paramMap.put("updatedAt", order.getUpdatedAt());

		return paramMap;
	}

	// 하나의 아이템으로부터 만들어진 애들은 같은 create, updateAt 을 갖는다고 볼 수 있다.
	private Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt,
		OrderItem item) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", orderId.toString().getBytes());
		paramMap.put("productId", item.productId().toString().getBytes());
		paramMap.put("category", item.category().toString());
		paramMap.put("price", item.price());
		paramMap.put("quantity", item.quantity());
		paramMap.put("createdAt", createdAt);
		paramMap.put("updatedAt", updatedAt);

		return paramMap;
	}
}
