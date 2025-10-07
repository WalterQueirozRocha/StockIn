package com.otaviowalter.stockin.services;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.transaction.PurchaseTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.SaleTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.TransactionDTO;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Purchases;
import com.otaviowalter.stockin.model.Sales;
import com.otaviowalter.stockin.model.Transaction;
import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.PurchasesRepository;
import com.otaviowalter.stockin.repositorys.SalesRepository;
import com.otaviowalter.stockin.repositorys.TransactionRepository;
import com.otaviowalter.stockin.repositorys.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PurchasesRepository purchasesRepository;

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private SalesRepository saleRepository;

	@Transactional(readOnly = true)
	public TransactionDTO findById(BigInteger id) {
		Transaction entity = transactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		return new TransactionDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<TransactionDTO> findAll(Pageable pageable) {
		try {
			Page<Transaction> pages = transactionRepository.findAll(pageable);
			return pages.map((transaction) -> new TransactionDTO(transaction));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("User Not Found");
		}
	}

	@Transactional
	public TransactionDTO create(TransactionDTO newTransaction) {
		Transaction transaction = new Transaction();

		if (newTransaction instanceof PurchaseTransactionDTO purchaseDTO && purchaseDTO.getPurchase() != null) {
			Purchases purchase = purchasesRepository.getReferenceById(purchaseDTO.getPurchase().getId());
			transaction.setPurchase(purchase);
			transaction.setTotalPrice(purchase.getTotalCost());
		}

		if (newTransaction instanceof SaleTransactionDTO saleDTO && saleDTO.getSale() != null) {
			Sales sale = saleRepository.getReferenceById(saleDTO.getSale().getId());
			transaction.setSales(sale);
			transaction.setTotalPrice(sale.getTotal());
		}

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());

		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(new Date());
		transaction.setUser(user);

		Transaction savedTransaction = transactionRepository.save(transaction);
		if (savedTransaction.getPurchase() != null) {
			return new PurchaseTransactionDTO(savedTransaction);
		} else {
			return new SaleTransactionDTO(savedTransaction);
		}
	}

	@Transactional
	public TransactionDTO update(BigInteger id, TransactionDTO dto) {
		try {
			Transaction transaction = transactionRepository.getReferenceById(id);
			transaction.setType(dto.getType());
			transaction.setTotalPrice(dto.getTotalPrice());
			transaction.setCreatedAt(new Date());

			Purchases purchase = purchasesRepository.getReferenceById(dto.getPurchase().getId());
			Users user = userRepository.getReferenceById(dto.getUser().getId());
			Sales sale = saleRepository.getReferenceById(dto.getSale().getId());

			transaction.setPurchase(purchase);
			transaction.setUser(user);
			transaction.setSales(sale);

			Transaction savedTransaction = transactionRepository.save(transaction);
			return new TransactionDTO(savedTransaction);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Transaction Not Found");
		}
	}

	@Transactional
	public void delete(BigInteger id) {
		try {
			transactionRepository.deleteById(id);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("User Not Found");
		}
	}

}
