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

import com.otaviowalter.stockin.dto.salesitems.SaleItemsDTO;
import com.otaviowalter.stockin.services.SaleItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/saleitems")
public class SaleItemController {

	@Autowired
	private SaleItemService service;

	@GetMapping("/{id}")
	public ResponseEntity<SaleItemsDTO> findById(@PathVariable UUID id) {
		SaleItemsDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	public ResponseEntity<Page<SaleItemsDTO>> findAllSaleItems(Pageable pageable) {
		Page<SaleItemsDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	public ResponseEntity<SaleItemsDTO> createSaleItem(@RequestBody @Valid SaleItemsDTO createSaleItem) {
		SaleItemsDTO createdSaleItem = service.create(createSaleItem);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdSaleItem.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdSaleItem);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<SaleItemsDTO> updateSaleItem(@PathVariable UUID id, @RequestBody @Valid SaleItemsDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteSaleItem(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
