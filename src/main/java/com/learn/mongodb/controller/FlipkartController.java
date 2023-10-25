package com.learn.mongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.mongodb.model.User;
import com.learn.mongodb.repository.FlipkartRepository;

@RestController
@RequestMapping("/order")
public class FlipkartController {
	
	@Autowired
	private FlipkartRepository repository;
	
	@PostMapping("/placeOrder")
	public String placeOrder(@RequestBody User user) {
		repository.save(user);
		return "Order placed successfully....";
	}
	
	@GetMapping("/getUserByName/{name}")
	public List<User> getUserByName(@PathVariable String name) {
		return repository.findByName(name);
	}
	
	@GetMapping("/getUserByAddress/{city}")
	public List<User> getUserByAddress(@PathVariable String city) {
		return repository.findByCity(city);
	}

}
