package com.otaviowalter.stockin.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.purchases.PurchasesDTO;
import com.otaviowalter.stockin.dto.purchasesitems.PurchaseItemsDTO;
import com.otaviowalter.stockin.dto.transaction.PurchaseTransactionDTO;
import com.otaviowalter.stockin.enums.TransactionENUM;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.PurchaseItems;
import com.otaviowalter.stockin.model.Purchases;
import com.otaviowalter.stockin.model.Supplier;
import com.otaviowalter.stockin.model.TransactionPurchase;
import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.PurchaseItemsRepository;
import com.otaviowalter.stockin.repositorys.PurchasesRepository;
import com.otaviowalter.stockin.repositorys.SupplierRepository;
import com.otaviowalter.stockin.repositorys.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PurchasesService {

	@Autowired
	private PurchasesRepository purchasesRepository;

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private PurchaseItemsRepository purchaseItemsRepository;

	@Autowired
	private PurchaseItemService purchaseItemService;

	@Autowired
	private StockService stockService;

	@Autowired
	private TransactionService transactionService;

	@Transactional(readOnly = true)
	public PurchasesDTO findById(UUID id) {
		Purchases entity = purchasesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));
		return new PurchasesDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<PurchasesDTO> findAll(Pageable pageable) {
		Page<Purchases> pages = purchasesRepository.findAll(pageable);
		return pages.map(PurchasesDTO::new);
	}

	@Transactional
	public PurchasesDTO create(PurchasesDTO newPurchase) {
		Purchases purchase = new Purchases();
		List<PurchaseItems> itemsList = new ArrayList<>();

		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal totalCost = BigDecimal.ZERO;

		Supplier supplier = supplierRepository.findById(newPurchase.getSupplier().getId())
				.orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
		
		Users user = usersRepository.findById(newPurchase.getUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		for (PurchaseItemsDTO itemDTO : newPurchase.getItemsList()) {
			PurchaseItemsDTO createdItemDTO = purchaseItemService.create(itemDTO);

			PurchaseItems item = purchaseItemsRepository.findById(createdItemDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Item not found"));

			itemsList.add(item);

			totalPrice = totalPrice.add(createdItemDTO.getSubTotalPrice());
			totalCost = totalCost.add(createdItemDTO.getSubTotalCost());

			stockService.registerEntry(createdItemDTO.getProduct().getId(), createdItemDTO.getQuantity(),
					createdItemDTO.getProduct().getCost());
		}

		purchase.setItemsList(itemsList);
		purchase.setTotalPrice(totalPrice);
		purchase.setTotalCost(totalCost);
		purchase.setDiscountPercentage(newPurchase.getDiscountPercentage());
		purchase.setObservation(newPurchase.getObservation());
		purchase.setSupplier(supplier);
		purchase.setCreatedAt(Instant.now());
		purchase.setUser(user);

		Purchases savedPurchase = purchasesRepository.save(purchase);

		PurchaseTransactionDTO transactionPurchase = createPurchaseTransactionDTO(savedPurchase);
		transactionService.createTransactionPurchase(transactionPurchase);

		return new PurchasesDTO(savedPurchase);
	}

	@Transactional
	public PurchasesDTO update(UUID id, PurchasesDTO dto) {
		Purchases purchase = purchasesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Purchase not found"));

		purchase.getItemsList().clear();
		
		Users user = usersRepository.findById(dto.getUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal totalCost = BigDecimal.ZERO;

		Supplier supplier = supplierRepository.findById(dto.getSupplier().getId())
				.orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

		for (PurchaseItemsDTO itemDTO : dto.getItemsList()) {
			PurchaseItemsDTO createdItemDTO = purchaseItemService.create(itemDTO);

			PurchaseItems item = purchaseItemsRepository.findById(createdItemDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Item not found"));

			purchase.getItemsList().add(item);

			totalPrice = totalPrice.add(createdItemDTO.getSubTotalPrice());
			totalCost = totalCost.add(createdItemDTO.getSubTotalCost());

			stockService.registerEntry(createdItemDTO.getProduct().getId(), createdItemDTO.getQuantity(),
					createdItemDTO.getProduct().getCost());
		}

		purchase.setTotalPrice(totalPrice);
		purchase.setTotalCost(totalCost);
		purchase.setDiscountPercentage(dto.getDiscountPercentage());
		purchase.setObservation(dto.getObservation());
		purchase.setSupplier(supplier);
		purchase.setCreatedAt(Instant.now());
		purchase.setUser(user);

		Purchases savedPurchase = purchasesRepository.save(purchase);

		return new PurchasesDTO(savedPurchase);

	}

	@Transactional
	public void delete(UUID id) {
		if (!purchasesRepository.existsById(id)) {
			throw new ResourceNotFoundException("Purchase not found");
		}
		purchasesRepository.deleteById(id);
	}

	private PurchaseTransactionDTO createPurchaseTransactionDTO(Purchases purchase) {
		TransactionPurchase transactionPurchase = new TransactionPurchase();

		transactionPurchase.setCreatedAt(purchase.getCreatedAt());
		transactionPurchase.setPurchase(purchase);
		transactionPurchase.setType(TransactionENUM.PURCHASE);
		transactionPurchase.setUser(purchase.getUser());

		return new PurchaseTransactionDTO(transactionPurchase);
	}

}