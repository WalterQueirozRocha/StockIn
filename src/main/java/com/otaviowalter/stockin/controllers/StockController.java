package com.otaviowalter.stockin.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otaviowalter.stockin.dto.stock.StockDTO;
import com.otaviowalter.stockin.services.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
	
	@Autowired
	private StockService service;
	
	@GetMapping
	public ResponseEntity<Page<StockDTO>> findAll(Pageable pageable) {
		Page<StockDTO> dtoPages = service.getAllStock(pageable);
		return ResponseEntity.ok(dtoPages);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StockDTO> findById(@PathVariable UUID id) {
		StockDTO findById = service.getStock(id);
		return ResponseEntity.ok(findById);
	}

}
