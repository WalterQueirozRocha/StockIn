package com.otaviowalter.stockin.controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otaviowalter.stockin.dto.transaction.AdjustmentTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.DevolutionTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.PurchaseTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.SaleTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.TransactionDTO;
import com.otaviowalter.stockin.services.TransactionService;

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

	@GetMapping("/sale/{id}")
	public ResponseEntity<SaleTransactionDTO> findSaleById(@PathVariable BigInteger id) {
		SaleTransactionDTO dto = service.findSaleById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/purchase/{id}")
	public ResponseEntity<PurchaseTransactionDTO> findPurchaseById(@PathVariable BigInteger id) {
		PurchaseTransactionDTO dto = service.findPurchaseById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/devolution/{id}")
	public ResponseEntity<DevolutionTransactionDTO> findDevolutionById(@PathVariable BigInteger id) {
		DevolutionTransactionDTO dto = service.findDevolutionById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/adjustment/{id}")
	public ResponseEntity<AdjustmentTransactionDTO> findAdjustmentById(@PathVariable BigInteger id) {
		AdjustmentTransactionDTO dto = service.findAdjustmentById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<Page<TransactionDTO>> findAllTransactions(Pageable pageable) {
		Page<TransactionDTO> dtoPages = service.findAll(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@GetMapping("/sales")
	public ResponseEntity<Page<SaleTransactionDTO>> findAllSalesTransactions(Pageable pageable) {
		Page<SaleTransactionDTO> dtoPages = service.findAllSales(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@GetMapping("/purchases")
	public ResponseEntity<Page<PurchaseTransactionDTO>> findAllPurchasesTransactions(Pageable pageable) {
		Page<PurchaseTransactionDTO> dtoPages = service.findAllPurchases(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@GetMapping("/devolutions")
	public ResponseEntity<Page<DevolutionTransactionDTO>> findAllDevolutionsTransactions(Pageable pageable) {
		Page<DevolutionTransactionDTO> dtoPages = service.findAllDevolutions(pageable);
		return ResponseEntity.ok(dtoPages);
	}

	@GetMapping("/adjustments")
	public ResponseEntity<Page<AdjustmentTransactionDTO>> findAllAdjustmentsTransactions(Pageable pageable) {
		Page<AdjustmentTransactionDTO> dtoPages = service.findAllAdjustments(pageable);
		return ResponseEntity.ok(dtoPages);
	}
}
