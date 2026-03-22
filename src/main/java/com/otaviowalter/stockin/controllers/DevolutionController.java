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

import com.otaviowalter.stockin.dto.devolutions.DevolutionDTO;
import com.otaviowalter.stockin.services.DevolutionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/devolutions")
@Tag(name = "Devolutions", description = "Devolutions management")
public class DevolutionController {

	@Autowired
	private DevolutionService service;

	@GetMapping("/{id}")
	@Operation(summary = "Find a devolution by ID")
	public ResponseEntity<DevolutionDTO> findById(@PathVariable UUID id) {
		DevolutionDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	@Operation(summary = "Find all devolutions")
	public ResponseEntity<Page<DevolutionDTO>> findAllSales(Pageable pageable) {
		Page<DevolutionDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	@Operation(summary = "Register a new devolution")
	public ResponseEntity<DevolutionDTO> createDevolution(@RequestBody @Valid DevolutionDTO createDevolution) {
		DevolutionDTO createdDevolution = service.create(createDevolution);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdDevolution.getId()).toUri();
		return ResponseEntity.created(uri).body(createdDevolution);
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Update a devolution by ID")
	public ResponseEntity<DevolutionDTO> updateDevolution(@PathVariable UUID id,
			@RequestBody @Valid DevolutionDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a devolution by ID")
	public ResponseEntity<Void> deleteDevolution(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
}
