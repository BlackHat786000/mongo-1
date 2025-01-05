package com.learn.mongodb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Setter
@Document(collection="addresses")
public class Address {

	@Id
	private String city;
	private String state;
	private String pinCode;

}
