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

import com.otaviowalter.stockin.dto.purchasesitems.PurchaseItemsDTO;
import com.otaviowalter.stockin.services.PurchaseItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/purchaseitem")
public class PurchaseItemController {

	@Autowired
	private PurchaseItemService service;

	@GetMapping("/{id}")
	public ResponseEntity<PurchaseItemsDTO> findById(@PathVariable UUID id) {
		PurchaseItemsDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	public ResponseEntity<Page<PurchaseItemsDTO>> findAllPurchaseItems(Pageable pageable) {
		Page<PurchaseItemsDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	public ResponseEntity<PurchaseItemsDTO> createPurchaseItem(@RequestBody @Valid PurchaseItemsDTO createPurchaseItem) {
		PurchaseItemsDTO createdPurchaseItem = service.create(createPurchaseItem);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdPurchaseItem.getId()).toUri();
		return ResponseEntity.created(uri).body(createdPurchaseItem);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PurchaseItemsDTO> updatePurchaseItem(@PathVariable UUID id,
			@RequestBody @Valid PurchaseItemsDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePurchaseItem(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
