package com.learn.mongodb.controller;

import com.learn.mongodb.model.Address;
import com.learn.mongodb.model.Product;
import com.learn.mongodb.repository.AddressRepository;
import com.learn.mongodb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

	@Autowired
	private MongoTemplate mongoTemplate;

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

	@PostMapping("/multi/product")
	public String updatePriceByQuantity(@RequestBody Product product) {
		// Create a query to match documents where quantity is the given value
		Query query = new Query(Criteria.where("quantity").is(product.getQuantity()));

		// Define the update operation to set the price
		Update update = new Update().set("price", product.getPrice());

		// Perform the update operation
		mongoTemplate.updateMulti(query, update, "products");

		return "Multiple products updated successfully...";
	}

}
