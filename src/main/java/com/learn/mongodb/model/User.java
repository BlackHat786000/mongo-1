package com.learn.mongodb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@Getter
@Setter
@Document(collection="order")
public class User {
	
	@Id
	private int id;
	private String name;
	private String gender;
	private Address address;	// One To One: one user have one address
	private List<Product> products;	//	One To Many: one user can buy many products

}
