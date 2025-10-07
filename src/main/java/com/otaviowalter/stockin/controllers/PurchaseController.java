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

import com.otaviowalter.stockin.dto.purchases.PurchasesDTO;
import com.otaviowalter.stockin.services.PurchasesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private PurchasesService service;

	@GetMapping("/{id}")
	public ResponseEntity<PurchasesDTO> findById(@PathVariable UUID id) {
		PurchasesDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	public ResponseEntity<Page<PurchasesDTO>> findAllPurchases(Pageable pageable) {
		Page<PurchasesDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	public ResponseEntity<PurchasesDTO> createPurchase(@RequestBody @Valid PurchasesDTO createPurchase) {
		PurchasesDTO createdPurchase = service.create(createPurchase);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPurchase.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdPurchase);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PurchasesDTO> updatePurchase(@PathVariable UUID id, @RequestBody @Valid PurchasesDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePurchase(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
