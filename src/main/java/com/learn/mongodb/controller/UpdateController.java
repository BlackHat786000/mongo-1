package com.learn.mongodb.controller;

import com.learn.mongodb.model.Address;
import com.learn.mongodb.model.Product;
import com.learn.mongodb.repository.AddressRepository;
import com.learn.mongodb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/address")
	public String updateAddress(@RequestBody Address address) {
		addressRepository.save(address);
		return "Address updated successfully...";
	}

	@PostMapping("/product")
	public String updateProduct(@RequestBody Product product) {
		productRepository.save(product);
		return "Product updated successfully...";
	}

}
