package com.otaviowalter.stockin.services;

import java.math.BigInteger;
import java.time.Instant;
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
import com.otaviowalter.stockin.enums.TransactionENUM;
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
import com.otaviowalter.stockin.repositorys.AdjustmentTransactionRepository;
import com.otaviowalter.stockin.repositorys.DevolutionTransactionRepository;
import com.otaviowalter.stockin.repositorys.ProductsRepository;
import com.otaviowalter.stockin.repositorys.PurchaseTransactionRepository;
import com.otaviowalter.stockin.repositorys.PurchasesRepository;
import com.otaviowalter.stockin.repositorys.SaleTransactionRepository;
import com.otaviowalter.stockin.repositorys.SalesRepository;
import com.otaviowalter.stockin.repositorys.TransactionRepository;
import com.otaviowalter.stockin.repositorys.UsersRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PurchaseTransactionRepository purchaseTransactionRepository;

	@Autowired
	private DevolutionTransactionRepository devolutionTransactionRepository;

	@Autowired
	private SaleTransactionRepository saleTransactionRepository;

	@Autowired
	private AdjustmentTransactionRepository adjustmentTransactionRepository;

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
				.orElseThrow(() -> new ResourceNotFoundException("Transaction Not Found"));
		return new TransactionDTO(entity);
	}

	@Transactional(readOnly = true)
	public PurchaseTransactionDTO findPurchaseById(BigInteger id) {
		TransactionPurchase entity = purchaseTransactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Purchase Not Found"));
		return new PurchaseTransactionDTO(entity);
	}

	@Transactional(readOnly = true)
	public AdjustmentTransactionDTO findAdjustmentById(BigInteger id) {
		TransactionAdjustment entity = adjustmentTransactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Adjustment Not Found"));
		return new AdjustmentTransactionDTO(entity);
	}

	@Transactional(readOnly = true)
	public DevolutionTransactionDTO findDevolutionById(BigInteger id) {
		TransactionDevolution entity = devolutionTransactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Devolution Not Found"));
		return new DevolutionTransactionDTO(entity);
	}

	@Transactional(readOnly = true)
	public SaleTransactionDTO findSaleById(BigInteger id) {
		TransactionSale entity = saleTransactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sale Not Found"));
		return new SaleTransactionDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<TransactionDTO> findAll(Pageable pageable) {
		try {
			Page<Transaction> pages = transactionRepository.findAll(pageable);
			return pages.map((transaction) -> new TransactionDTO(transaction));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Transactions Not Found");
		}
	}

	@Transactional(readOnly = true)
	public Page<PurchaseTransactionDTO> findAllPurchases(Pageable pageable) {
		try {
			Page<TransactionPurchase> pages = purchaseTransactionRepository.findAll(pageable);
			return pages.map((transaction) -> new PurchaseTransactionDTO(transaction));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Purchases Not Found");
		}
	}

	@Transactional(readOnly = true)
	public Page<AdjustmentTransactionDTO> findAllAdjustments(Pageable pageable) {
		try {
			Page<TransactionAdjustment> pages = adjustmentTransactionRepository.findAll(pageable);
			return pages.map((transaction) -> new AdjustmentTransactionDTO(transaction));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Adjustments Not Found");
		}
	}

	@Transactional(readOnly = true)
	public Page<DevolutionTransactionDTO> findAllDevolutions(Pageable pageable) {
		try {
			Page<TransactionDevolution> pages = devolutionTransactionRepository.findAll(pageable);
			return pages.map((transaction) -> new DevolutionTransactionDTO(transaction));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Devolutions Not Found");
		}
	}

	@Transactional(readOnly = true)
	public Page<SaleTransactionDTO> findAllSales(Pageable pageable) {
		try {
			Page<TransactionSale> pages = saleTransactionRepository.findAll(pageable);
			return pages.map((transaction) -> new SaleTransactionDTO(transaction));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Sales Not Found");
		}
	}

	@Transactional
	public SaleTransactionDTO createTransactionSale(SaleTransactionDTO newTransaction) {
		TransactionSale transaction = new TransactionSale();
		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(Instant.now());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		Sales sale = saleRepository.getReferenceById(newTransaction.getSale().getId());
		transaction.setSales(sale);

		TransactionSale savedTransaction = saleTransactionRepository.save(transaction);
		return new SaleTransactionDTO(savedTransaction);
	}

	@Transactional
	public PurchaseTransactionDTO createTransactionPurchase(PurchaseTransactionDTO newTransaction) {
		TransactionPurchase transaction = new TransactionPurchase();
		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(Instant.now());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		Purchases purchase = purchasesRepository.getReferenceById(newTransaction.getPurchase().getId());
		transaction.setPurchase(purchase);

		TransactionPurchase savedTransaction = purchaseTransactionRepository.save(transaction);
		return new PurchaseTransactionDTO(savedTransaction);
	}

	@Transactional
	public AdjustmentTransactionDTO createTransactionAdjustment(AdjustmentTransactionDTO newTransaction) {
		TransactionAdjustment transaction = new TransactionAdjustment();
		transaction.setType(TransactionENUM.ADJUSTMENT);
		transaction.setCreatedAt(Instant.now());

		transaction.setProductAfterAdjustment(newTransaction.getProductAfterAdjustment().toEntity());
		transaction.setProductBeforeAdjustment(newTransaction.getProductBeforeAdjustment().toEntity());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		TransactionAdjustment savedTransaction = adjustmentTransactionRepository.save(transaction);
		return new AdjustmentTransactionDTO(savedTransaction);
	}

	@Transactional
	public DevolutionTransactionDTO createTransactionDevolution(DevolutionTransactionDTO newTransaction) {
		TransactionDevolution transaction = new TransactionDevolution();
		transaction.setType(newTransaction.getType());
		transaction.setCreatedAt(Instant.now());

		Users user = userRepository.getReferenceById(newTransaction.getUser().getId());
		transaction.setUser(user);

		List<Products> products = newTransaction.getItems().stream()
				.map(itemDTO -> productRepository.getReferenceById(itemDTO.getId())).collect(Collectors.toList());
		transaction.setItems(products);

		Sales sale = saleRepository.getReferenceById(newTransaction.getSales().getId());
		transaction.setSales(sale);

		TransactionDevolution savedTransaction = devolutionTransactionRepository.save(transaction);
		return new DevolutionTransactionDTO(savedTransaction);
	}

}
