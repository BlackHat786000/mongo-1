package com.learn.mongodb.controller;

import com.learn.mongodb.model.User;
import com.learn.mongodb.repository.FlipkartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
