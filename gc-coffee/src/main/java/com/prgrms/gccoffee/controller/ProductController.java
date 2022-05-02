package com.prgrms.gccoffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.prgrms.gccoffee.model.Product;
import com.prgrms.gccoffee.service.ProductService;

@Controller
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public String productsPage(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);

		return "product-list";
	}

	@GetMapping("new-product")
	public String newProductPage() {
		return "new-product";
	}

	@PostMapping("/products")
	public String newProduct(CreateProductRequest createProductRequest) {
		Product product = productService.createProduct(createProductRequest.productName(),
			createProductRequest.category(), createProductRequest.price(), createProductRequest.description());

		return "redirect:/products";
	}
}
