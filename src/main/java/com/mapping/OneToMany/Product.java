package com.mapping.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "product")
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	
	private Category category;
	
	public Product() {
		super();
	}

	public Product(int id, String name, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
	}
	
	
}
