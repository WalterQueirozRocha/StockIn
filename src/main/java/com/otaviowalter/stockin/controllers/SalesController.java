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

import com.otaviowalter.stockin.dto.sales.SalesDTO;
import com.otaviowalter.stockin.services.SalesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
@Tag(name = "Sales", description = "Sales management")
public class SalesController {

	@Autowired
	private SalesService service;

	@GetMapping("/{id}")
	@Operation(summary = "Find a sale by ID")
	public ResponseEntity<SalesDTO> findById(@PathVariable UUID id) {
		SalesDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	@Operation(summary = "Find all sales")
	public ResponseEntity<Page<SalesDTO>> findAllSales(Pageable pageable) {
		Page<SalesDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	@Operation(summary = "Register a new sale", description = "Register a new sale if there are enough products in stock")
	public ResponseEntity<SalesDTO> createSale(@RequestBody @Valid SalesDTO createSale) {
		SalesDTO createdSale = service.create(createSale);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdSale.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdSale);
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Update a sale by ID")
	public ResponseEntity<SalesDTO> updateSale(@PathVariable UUID id, @RequestBody @Valid SalesDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a sale by ID")
	public ResponseEntity<Void> deleteSale(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
