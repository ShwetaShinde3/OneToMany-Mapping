package com.mapping.OneToMany;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository cr;
	
	@GetMapping("")
	public List<Category> getAllCategories()
	{
	
	List<Category> categories = cr.findAll();
	System.out.println("Categories fetched: " + categories.size());
	return categories;
	}
	
	@GetMapping("/{id}")
	public Category getId(@PathVariable int id)
	{
	return cr.findById(id).orElse(null);
	}

	@PostMapping("")
	public Category saveAll( @RequestBody Category c)
	{
		
	Category ce = new Category();
	ce.setName(c.getName());
	List<Product> list = new ArrayList<>();
	for (Product p : c.getProduct())
	{
	Product pe = new Product();
	pe.setCategory(ce);
	pe.setName(p.getName());
	list.add(pe);
	}
	ce.setProduct(list);
	return cr.save(ce);
	}

	@PutMapping("/{id}")
	public Category putId(@PathVariable int id, @RequestBody Category c)
	{
	Category ce = cr.findById(id).orElseThrow();
	ce.setName(c.getName());
	List<Product> list = ce.getProduct();
	for (Product p : c.getProduct()) {
	    Product pe = new Product();
	    pe.setCategory(ce);
	    pe.setName(p.getName());
	    list.add(pe);
	}
	ce.setProduct(list);
	
	
	return cr.save(ce);
	}

	@DeleteMapping("/{id}")
	public void deleteId(@PathVariable int id)
	{
	cr.deleteById(id);
	}
	
	@GetMapping("/{pageNo}/{pageSize}")
	public List<Category> getPaginated(@PathVariable int pageNo, @PathVariable
	int pageSize)
	{
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	Page<Category> pagedResult = cr.findAll(pageable);
	return pagedResult.hasContent() ? pagedResult.getContent() : new
	ArrayList<Category>();
	}
	
	@GetMapping("/sort")
	public List<Category> getAllSorted(@RequestParam String field, @RequestParam
	String direction)
	{
	Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
	Sort.Direction.DESC : Sort.Direction.ASC;
	return cr.findAll(Sort.by(sortDirection, field));
	}
	@GetMapping("/page/{pageNo}/{pageSize}/sort")
	public List<Category> getPaginatedAndSorted(@PathVariable int pageNo,
	@PathVariable int pageSize,

	@RequestParam String sortField, @RequestParam String

	sortDir)
	{
	Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
	Sort.by(sortField).ascending() :

	Sort.by(sortField).descending();
	Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
	Page<Category> pagedResult = cr.findAll(pageable);
	return pagedResult.hasContent() ? pagedResult.getContent() : new
	ArrayList<Category>();
	}
}
