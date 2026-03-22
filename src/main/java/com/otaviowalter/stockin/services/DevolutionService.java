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

import com.otaviowalter.stockin.dto.devolutionitems.DevolutionItemsDTO;
import com.otaviowalter.stockin.dto.devolutions.DevolutionDTO;
import com.otaviowalter.stockin.dto.transaction.DevolutionTransactionDTO;
import com.otaviowalter.stockin.enums.TransactionENUM;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Devolution;
import com.otaviowalter.stockin.model.DevolutionItems;
import com.otaviowalter.stockin.model.SaleItems;
import com.otaviowalter.stockin.model.Sales;
import com.otaviowalter.stockin.model.TransactionDevolution;
import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.DevolutionItemsRepository;
import com.otaviowalter.stockin.repositorys.DevolutionRepository;
import com.otaviowalter.stockin.repositorys.SalesRepository;
import com.otaviowalter.stockin.repositorys.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DevolutionService {

	@Autowired
	private DevolutionRepository devolutionRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private DevolutionItemsRepository devolutionItemsRepository;

	@Autowired
	private SalesRepository saleRepository;

	@Autowired
	private DevolutionItemsService devolutionItemsService;

	@Autowired
	private StockService stockService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AuthorizationService authorizationService;

	@Transactional(readOnly = true)
	public DevolutionDTO findById(UUID id) {
		Devolution entity = devolutionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
		return new DevolutionDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<DevolutionDTO> findAll(Pageable pageable) {
		Page<Devolution> pages = devolutionRepository.findAll(pageable);
		return pages.map(DevolutionDTO::new);
	}

	@Transactional
	public DevolutionDTO create(DevolutionDTO newDevolution) {
		Devolution devolution = new Devolution();
		List<DevolutionItems> itemsList = new ArrayList<>();
		BigDecimal totalPrice = BigDecimal.ZERO;

		Users user = usersRepository.findById(authorizationService.getAuthenticatedUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		Sales sale = saleRepository.getReferenceById(newDevolution.getSale().getId());

		for (DevolutionItemsDTO itemDTO : newDevolution.getProductList()) {
			SaleItems saleItem = sale.getProductList().stream()
					.filter(si -> si.getProduct().getId().equals(itemDTO.getProduct().getId())).findFirst()
					.orElseThrow(() -> new EntityNotFoundException("Product was not part of this sale"));

			if (itemDTO.getQuantity() > saleItem.getQuantity()) {
				throw new EntityNotFoundException("Returned quantity (" + itemDTO.getQuantity()
						+ ") exceeds sold quantity (" + saleItem.getQuantity() + ")");
			}

			DevolutionItemsDTO createdItemDTO = devolutionItemsService.create(itemDTO);
			DevolutionItems item = devolutionItemsRepository.findById(createdItemDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Item not found"));

			itemsList.add(item);
			totalPrice = totalPrice.add(createdItemDTO.getSubTotalPrice());

			if (Boolean.TRUE.equals(item.getIsResaleable())) {
				stockService.registerEntry(createdItemDTO.getProduct().getId(), createdItemDTO.getQuantity(),
						createdItemDTO.getProduct().getCost());
			}
		}

		devolution.setSales(sale);
		devolution.setCreatedAt(Instant.now());
		devolution.setProductList(itemsList);
		devolution.setTotal(totalPrice);
		devolution.setUser(user);

		Devolution savedDevolution = devolutionRepository.save(devolution);

		DevolutionTransactionDTO transactionDevolution = createDevolutionTransactionDTO(savedDevolution);
		transactionService.createTransactionDevolution(transactionDevolution);

		return new DevolutionDTO(savedDevolution);
	}

	@Transactional
	public DevolutionDTO update(UUID id, DevolutionDTO dto) {
		Devolution devolution = devolutionRepository.getReferenceById(id);

		List<DevolutionItems> itemsList = new ArrayList<>();
		BigDecimal totalPrice = BigDecimal.ZERO;

		Users user = usersRepository.findById(authorizationService.getAuthenticatedUser().getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		
		Sales sale = saleRepository.getReferenceById(dto.getSale().getId());

		for (DevolutionItemsDTO itemDTO : dto.getProductList()) {
			SaleItems saleItem = sale.getProductList().stream()
					.filter(si -> si.getProduct().getId().equals(itemDTO.getProduct().getId())).findFirst()
					.orElseThrow(() -> new EntityNotFoundException("Product was not part of this sale"));

			if (itemDTO.getQuantity() > saleItem.getQuantity()) {
				throw new EntityNotFoundException("Returned quantity (" + itemDTO.getQuantity()
						+ ") exceeds sold quantity (" + saleItem.getQuantity() + ")");
			}

			DevolutionItemsDTO createdItemDTO = devolutionItemsService.create(itemDTO);
			DevolutionItems item = devolutionItemsRepository.findById(createdItemDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Item not found"));

			itemsList.add(item);
			totalPrice = totalPrice.add(createdItemDTO.getSubTotalPrice());

			if (Boolean.TRUE.equals(item.getIsResaleable())) {
				stockService.registerEntry(createdItemDTO.getProduct().getId(), createdItemDTO.getQuantity(),
						createdItemDTO.getProduct().getCost());
			}
		}

		devolution.setCreatedAt(Instant.now());
		devolution.setProductList(itemsList);
		devolution.setTotal(totalPrice);
		devolution.setUser(user);

		Devolution savedDevolution = devolutionRepository.save(devolution);

		DevolutionTransactionDTO transactionDevolution = createDevolutionTransactionDTO(savedDevolution);
		transactionService.createTransactionDevolution(transactionDevolution);

		return new DevolutionDTO(savedDevolution);
	}

	@Transactional
	public void delete(UUID id) {
		if (!devolutionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Sale not found");
		}
		devolutionRepository.deleteById(id);
	}

	private DevolutionTransactionDTO createDevolutionTransactionDTO(Devolution devolution) {
		TransactionDevolution transactionDevolution = new TransactionDevolution();

		transactionDevolution.setCreatedAt(devolution.getCreatedAt());
		transactionDevolution.setDevolution(devolution);
		transactionDevolution.setType(TransactionENUM.DEVOLUTION);
		transactionDevolution.setUser(devolution.getUser());

		return new DevolutionTransactionDTO(transactionDevolution);

	}
}
