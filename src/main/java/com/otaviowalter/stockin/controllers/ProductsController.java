package com.otaviowalter.stockin.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.otaviowalter.stockin.dto.products.ProductsCreateDTO;
import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.services.ProductsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Products management")
public class ProductsController {

	@Autowired
	private ProductsService service;

	@GetMapping("/{id}")
	@Operation(summary = "Find a product by ID")
	public ResponseEntity<ProductsDTO> findById(@PathVariable UUID id) {
		ProductsDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	@Operation(summary = "Find all products")
	public ResponseEntity<Page<ProductsDTO>> findAllProducts(Pageable pageable) {
		Page<ProductsDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/create")
	@Operation(summary = "Register a new product")
	public ResponseEntity<ProductsDTO> createProduct(@RequestBody @Valid ProductsCreateDTO createProduct) {
		ProductsDTO createdProduct = service.create(createProduct);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdProduct.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdProduct);
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Update a product by ID")
	public ResponseEntity<ProductsDTO> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductsDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a product by ID")
	public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
