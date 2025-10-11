package com.otaviowalter.stockin.controllers;

import java.math.BigInteger;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.otaviowalter.stockin.dto.transaction.AdjustmentTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.DevolutionTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.PurchaseTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.SaleTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.TransactionDTO;
import com.otaviowalter.stockin.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDTO> findById(@PathVariable BigInteger id) {
		TransactionDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<Page<TransactionDTO>> findAllTransactions(Pageable pageable) {
		Page<TransactionDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/sale")
	public ResponseEntity<SaleTransactionDTO> createTransactionSale(@RequestBody @Valid SaleTransactionDTO dto) {
		SaleTransactionDTO created = service.createTransactionSale(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(uri).body(created);
	}

	@PostMapping("/purchase")
	public ResponseEntity<PurchaseTransactionDTO> createTransactionPurchase(
			@RequestBody @Valid PurchaseTransactionDTO dto) {
		PurchaseTransactionDTO created = service.createTransactionPurchase(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(uri).body(created);
	}

	@PostMapping("/adjustment")
	public ResponseEntity<AdjustmentTransactionDTO> createTransactionAdjustment(
			@RequestBody @Valid AdjustmentTransactionDTO dto) {
		AdjustmentTransactionDTO created = service.createTransactionAdjustment(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(uri).body(created);
	}

	@PostMapping("/devolution")
	public ResponseEntity<DevolutionTransactionDTO> createTransactionDevolution(
			@RequestBody @Valid DevolutionTransactionDTO dto) {
		DevolutionTransactionDTO created = service.createTransactionDevolution(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(uri).body(created);
	}

}
