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

import com.otaviowalter.stockin.dto.sales.SalesDTO;
import com.otaviowalter.stockin.dto.salesitems.SaleItemsDTO;
import com.otaviowalter.stockin.dto.transaction.SaleTransactionDTO;
import com.otaviowalter.stockin.enums.TransactionENUM;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.SaleItems;
import com.otaviowalter.stockin.model.Sales;
import com.otaviowalter.stockin.model.TransactionSale;
import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.SaleItemsRepository;
import com.otaviowalter.stockin.repositorys.SalesRepository;
import com.otaviowalter.stockin.repositorys.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SalesService {

	@Autowired
	private SalesRepository salesRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private SaleItemService saleItemService;

	@Autowired
	private SaleItemsRepository saleItemsRepository;

	@Autowired
	private StockService stockService;
	
	@Autowired
	private TransactionService transactionService;

	@Transactional(readOnly = true)
	public SalesDTO findById(UUID id) {
		Sales entity = salesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
		return new SalesDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SalesDTO> findAll(Pageable pageable) {
		Page<Sales> pages = salesRepository.findAll(pageable);
		return pages.map(SalesDTO::new);
	}

	@Transactional
	public SalesDTO create(SalesDTO newSale) {
		Sales sale = new Sales();
		List<SaleItems> itemsList = new ArrayList<>();
		BigDecimal totalPrice = BigDecimal.ZERO;

		Users user = usersRepository.findById(newSale.getUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		for (SaleItemsDTO itemDTO : newSale.getProductList()) {
			SaleItemsDTO createdItemDTO = saleItemService.create(itemDTO);

			SaleItems item = saleItemsRepository.findById(createdItemDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Item not found"));

			itemsList.add(item);

			totalPrice = totalPrice.add(createdItemDTO.getSubTotalPrice());
			stockService.registerExit(createdItemDTO.getProduct().getId(), createdItemDTO.getQuantity());
		}

		sale.setCreatedAt(Instant.now());
		sale.setDiscountPercentage(newSale.getDiscountPercentage());
		sale.setProductList(itemsList);
		sale.setTotal(totalPrice);
		sale.setUser(user);

		Sales savedSale = salesRepository.save(sale);
		
		SaleTransactionDTO transactionSale = createSaleTransactionDTO(savedSale);
		transactionService.createTransactionSale(transactionSale);
		
		return new SalesDTO(savedSale);
	}

	@Transactional
	public SalesDTO update(UUID id, SalesDTO dto) {
		Sales sale = salesRepository.getReferenceById(id);

		sale.getProductList().clear();

		Users user = usersRepository.findById(dto.getUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		BigDecimal totalPrice = BigDecimal.ZERO;

		sale.getProductList().clear();

		for (SaleItemsDTO itemDTO : dto.getProductList()) {
			SaleItemsDTO createdItemDTO = saleItemService.create(itemDTO);

			SaleItems item = saleItemsRepository.findById(createdItemDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Item not found"));

			sale.getProductList().add(item);

			totalPrice = totalPrice.add(createdItemDTO.getSubTotalPrice());
			stockService.registerExit(createdItemDTO.getProduct().getId(), createdItemDTO.getQuantity());
		}

		sale.setCreatedAt(Instant.now());
		sale.setDiscountPercentage(dto.getDiscountPercentage());
		sale.setTotal(totalPrice);
		sale.setUser(user);

		Sales savedSale = salesRepository.save(sale);
		return new SalesDTO(savedSale);

	}

	@Transactional
	public void delete(UUID id) {
		if (!salesRepository.existsById(id)) {
			throw new ResourceNotFoundException("Sale not found");
		}
		salesRepository.deleteById(id);
	}

	private SaleTransactionDTO createSaleTransactionDTO(Sales sale) {
		TransactionSale transactionSale = new TransactionSale();

		transactionSale.setCreatedAt(sale.getCreatedAt());
		transactionSale.setSales(sale);
		transactionSale.setType(TransactionENUM.SALE);
		transactionSale.setUser(sale.getUser());

		return new SaleTransactionDTO(transactionSale);

	}
}