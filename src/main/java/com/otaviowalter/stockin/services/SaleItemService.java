package com.otaviowalter.stockin.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.salesitems.SaleItemsDTO;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.model.SaleItems;
import com.otaviowalter.stockin.repositorys.ProductsRepository;
import com.otaviowalter.stockin.repositorys.SaleItemsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SaleItemService {

	@Autowired
	private SaleItemsRepository saleItemsRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@Transactional(readOnly = true)
	public SaleItemsDTO findById(UUID id) {
		SaleItems entity = saleItemsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item Not Found"));
		return new SaleItemsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleItemsDTO> findAll(Pageable pageable) {
		try {
			Page<SaleItems> pages = saleItemsRepository.findAll(pageable);
			return pages.map((saleItems) -> new SaleItemsDTO(saleItems));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Item Not Found");
		}
	}

	@Transactional
	public SaleItemsDTO create(SaleItemsDTO newSaleItem) {
		SaleItems saleItems = new SaleItems();
		Products product = productsRepository.getReferenceById(newSaleItem.getProduct().getId());
		saleItems.setProduct(product);

		int quantity = newSaleItem.getQuantity();
		saleItems.setQuantity(quantity);

		BigDecimal unitPrice = product.getPrice();

		BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
		saleItems.setSubTotalPrice(totalPrice);
		
		saleItems.setDescription(product.getDescription());

		saleItems.setCreatedAt(Instant.now());

		SaleItems savedSaleItems = saleItemsRepository.save(saleItems);
		return new SaleItemsDTO(savedSaleItems);
	}

	@Transactional
	public SaleItemsDTO update(UUID id, SaleItemsDTO dto) {
		try {
			SaleItems entity = saleItemsRepository.getReferenceById(id);

			Products newProduct = productsRepository.getReferenceById(dto.getProduct().getId());
			entity.setProduct(newProduct);

			int newQuantity = dto.getQuantity();
			entity.setQuantity(newQuantity);

			BigDecimal unitPrice = newProduct.getPrice();

			BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(newQuantity));
			entity.setSubTotalPrice(totalPrice);
			
			entity.setDescription(newProduct.getDescription());

			entity.setCreatedAt(Instant.now());

			return new SaleItemsDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Sale Item Not Found");
		}
	}

	@Transactional
	public void delete(UUID id) {
		try {
			saleItemsRepository.deleteById(id);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Item Not Found");
		}

	}
}