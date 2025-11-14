package com.otaviowalter.stockin.services;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.transaction.AdjustmentTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.DevolutionTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.PurchaseTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.SaleTransactionDTO;
import com.otaviowalter.stockin.dto.transaction.TransactionDTO;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.model.Purchases;
import com.otaviowalter.stockin.model.Sales;
import com.otaviowalter.stockin.model.Transaction;
import com.otaviowalter.stockin.model.TransactionAdjustment;
import com.otaviowalter.stockin.model.TransactionDevolution;
import com.otaviowalter.stockin.model.TransactionPurchase;
import com.otaviowalter.stockin.model.TransactionSale;
import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.ProductsRepository;
import com.otaviowalter.stockin.repositorys.PurchasesRepository;
import com.otaviowalter.stockin.repositorys.SalesRepository;
import com.otaviowalter.stockin.repositorys.TransactionRepository;
import com.otaviowalter.stockin.repositorys.UsersRepository;

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

	@Autowired
	private ProductsRepository productRepository;

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
	public SaleTransactionDTO createTransactionSale(SaleTransactionDTO newTransaction) {
		TransactionSale transaction = new TransactionSale();
		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(new Date());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		Sales sale = saleRepository.getReferenceById(newTransaction.getSale().getId());
		transaction.setSales(sale);

		return new SaleTransactionDTO(transaction);
	}

	@Transactional
	public PurchaseTransactionDTO createTransactionPurchase(PurchaseTransactionDTO newTransaction) {
		TransactionPurchase transaction = new TransactionPurchase();
		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(new Date());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		Purchases purchase = purchasesRepository.getReferenceById(newTransaction.getPurchase().getId());
		transaction.setPurchase(purchase);

		return new PurchaseTransactionDTO(transaction);
	}

	@Transactional
	public AdjustmentTransactionDTO createTransactionAdjustment(AdjustmentTransactionDTO newTransaction) {
		TransactionAdjustment transaction = new TransactionAdjustment();
		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(new Date());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		List<Products> products = newTransaction.getItems().stream()
				.map(itemDTO -> productRepository.getReferenceById(itemDTO.getId())).collect(Collectors.toList());
		transaction.setItems(products);

		return new AdjustmentTransactionDTO(transaction);
	}

	@Transactional
	public DevolutionTransactionDTO createTransactionDevolution(DevolutionTransactionDTO newTransaction) {
		TransactionDevolution transaction = new TransactionDevolution();
		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(new Date());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		List<Products> products = newTransaction.getItems().stream()
				.map(itemDTO -> productRepository.getReferenceById(itemDTO.getId())).collect(Collectors.toList());
		transaction.setItems(products);

		Sales sale = saleRepository.getReferenceById(newTransaction.getSales().getId());
		transaction.setSales(sale);

		return new DevolutionTransactionDTO(transaction);
	}

}
