package com.prgrms.gccoffee.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.gccoffee.controller.CreateOrderRequest;
import com.prgrms.gccoffee.model.Email;
import com.prgrms.gccoffee.model.Order;
import com.prgrms.gccoffee.service.OrderService;

@RestController
public class OrderRestController {
	private final OrderService orderService;

	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/api/v1/orders")
	public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
		System.out.println("createOrderRequest = " + createOrderRequest);
		return orderService.createOrder(
			new Email(createOrderRequest.email()),
			createOrderRequest.address(),
			createOrderRequest.postcode(),
			createOrderRequest.orderItems()
		);
	}
}
