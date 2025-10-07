package com.otaviowalter.stockin.controllers;

import java.math.BigInteger;
import java.net.URI;

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
		TransactionDTO findById = service.findById(id);
		return ResponseEntity.ok(findById);
	}

	@GetMapping
	public ResponseEntity<Page<TransactionDTO>> findAllTransactions(Pageable pageable) {
		Page<TransactionDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@PostMapping("/register")
	public ResponseEntity<TransactionDTO> createTransaction(@RequestBody @Valid TransactionDTO createTransaction) {
		TransactionDTO createdTransaction = service.create(createTransaction);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdTransaction.getId()).toUri();
		return ResponseEntity.created(uri).body(createdTransaction);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable BigInteger id,
			@RequestBody @Valid TransactionDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable BigInteger id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
