package com.mapping.OneToMany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL )
	
	private List<Product> product;
	
	public Category() {
		super();
		
	}

	public Category(int id, String name, List<Product> product) {
		super();
		this.id = id;
		this.name = name;
		this.product = product;
	}
	
	
}
