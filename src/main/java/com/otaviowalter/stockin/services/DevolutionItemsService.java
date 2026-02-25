package com.otaviowalter.stockin.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.devolutionitems.DevolutionItemsDTO;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.DevolutionItems;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.repositorys.DevolutionItemsRepository;
import com.otaviowalter.stockin.repositorys.ProductsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DevolutionItemsService {

	@Autowired
	private DevolutionItemsRepository devolutionItemsRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@Transactional(readOnly = true)
	public DevolutionItemsDTO findById(UUID id) {
		DevolutionItems entity = devolutionItemsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item Not Found"));
		return new DevolutionItemsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<DevolutionItemsDTO> findAll(Pageable pageable) {
		try {
			Page<DevolutionItems> pages = devolutionItemsRepository.findAll(pageable);
			return pages.map((devolutionItems) -> new DevolutionItemsDTO(devolutionItems));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Item Not Found");
		}
	}

	@Transactional
	public DevolutionItemsDTO create(DevolutionItemsDTO newDevolutionItem) {
		DevolutionItems devolutionItems = new DevolutionItems();
		Products product = productsRepository.getReferenceById(newDevolutionItem.getProduct().getId());
		devolutionItems.setProduct(product);

		int quantity = newDevolutionItem.getQuantity();
		devolutionItems.setQuantity(quantity);

		BigDecimal unitPrice = product.getPrice();

		BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
		devolutionItems.setSubTotalPrice(totalPrice);

		devolutionItems.setDescription(product.getDescription());
		devolutionItems.setCreatedAt(Instant.now());
		devolutionItems.setIsResaleable(newDevolutionItem.getIsResaleable());

		DevolutionItems savedDevolutionItems = devolutionItemsRepository.save(devolutionItems);
		return new DevolutionItemsDTO(savedDevolutionItems);
	}

	@Transactional
	public DevolutionItemsDTO update(UUID id, DevolutionItemsDTO dto) {
		try {
			DevolutionItems entity = devolutionItemsRepository.getReferenceById(id);

			Products product = productsRepository.getReferenceById(dto.getProduct().getId());
			entity.setProduct(product);

			int quantity = dto.getQuantity();
			entity.setQuantity(quantity);

			BigDecimal unitPrice = product.getPrice();

			BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
			entity.setSubTotalPrice(totalPrice);

			entity.setDescription(product.getDescription());
			entity.setCreatedAt(Instant.now());
			entity.setIsResaleable(dto.getIsResaleable());

			DevolutionItems savedDevolutionItems = devolutionItemsRepository.save(entity);
			return new DevolutionItemsDTO(savedDevolutionItems);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Devolution Item Not Found");
		}
	}

	@Transactional
	public void delete(UUID id) {
		try {
			devolutionItemsRepository.deleteById(id);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Item Not Found");
		}

	}
}
