package com.mapping.OneToMany;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductRepository pr;
	
	@GetMapping()
	public List<Product> getAllProducts()
	{
	return pr.findAll();
	}
	
	@GetMapping("/{id}")
	public Product getId(@PathVariable int id)
	{
	return pr.findById(id).orElse(null);
	}
	@PostMapping("")
	public Product saveAll(@RequestBody Product p)
	{
		
	return pr.save(p);
	}
	@PutMapping("/{id}")
	public Product putId(@PathVariable int id, @RequestBody Product p)
	{
	Product existingProduct = pr.findById(id).orElseThrow();
	
	existingProduct.setName(p.getName());
	return pr.save(existingProduct);
	}

	@DeleteMapping("/{id}")
	public void deleteId(@PathVariable int id)
	{
	pr.deleteById(id);
	}
	
	@GetMapping("/sort")
	public List<Product> getAllSorted(@RequestParam String field, @RequestParam String
	direction)
	{
	Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
	Sort.Direction.DESC : Sort.Direction.ASC;
	return pr.findAll(Sort.by(sortDirection, field));
	}
	
	@GetMapping("/{pageNo}/{pageSize}")
	public List<Product> getPaginatedAndSorted(@PathVariable int pageNo, @PathVariable
	int pageSize)
{
	
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Product> pagedResult = pr.findAll(pageable);
	return pagedResult.hasContent() ? pagedResult.getContent() : new
	ArrayList<Product>();
	}
	
	@GetMapping("/page/{pageNo}/{pageSize}/{field}/{direction}")
	public List<Product> getPaginatedAndSorted(@PathVariable int pageNo, @PathVariable
	int pageSize,

	@PathVariable String field, @PathVariable String direction)

	{
	Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
	Sort.by(field).ascending() :

	Sort.by(field).descending();
	Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
	Page<Product> pagedResult = pr.findAll(pageable);
	return pagedResult.hasContent() ? pagedResult.getContent() : new
	ArrayList<Product>();
	}
}
