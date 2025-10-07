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

import com.otaviowalter.stockin.dto.categorys.CategorysDTO;
import com.otaviowalter.stockin.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping("/{id}")
	public ResponseEntity<CategorysDTO> findById(@PathVariable UUID id) {
		CategorysDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	public ResponseEntity<Page<CategorysDTO>> findAllCategorys(Pageable pageable) {
		Page<CategorysDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/create")
	public ResponseEntity<CategorysDTO> createCategory(@RequestBody @Valid CategorysDTO createCategory) {
		CategorysDTO createdCategory = service.create(createCategory);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCategory.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdCategory);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CategorysDTO> updateCategory(@PathVariable UUID id, @RequestBody @Valid CategorysDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
