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

import com.otaviowalter.stockin.dto.supplier.SupplierDTO;
import com.otaviowalter.stockin.services.SupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	@Autowired
	private SupplierService service;

	@GetMapping("/{id}")
	public ResponseEntity<SupplierDTO> findById(@PathVariable UUID id) {
		SupplierDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	public ResponseEntity<Page<SupplierDTO>> findAllSuppliers(Pageable pageable) {
		Page<SupplierDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	public ResponseEntity<SupplierDTO> createSupplier(@RequestBody @Valid SupplierDTO createSupplier) {
		SupplierDTO createdSupplier = service.create(createSupplier);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdSupplier.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdSupplier);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable UUID id, @RequestBody @Valid SupplierDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
