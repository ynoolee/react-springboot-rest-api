package com.prgrms.gccoffee.controller;

import java.util.List;

import com.prgrms.gccoffee.model.OrderItem;

public record CreateOrderRequest(
	String email, String address, String postcode, List<OrderItem> orderItems
) {
}
