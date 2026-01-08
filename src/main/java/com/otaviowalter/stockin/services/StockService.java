package com.otaviowalter.stockin.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.otaviowalter.stockin.dto.products.ProductStockDTO;
import com.otaviowalter.stockin.dto.stock.StockDTO;
import com.otaviowalter.stockin.enums.MovementTypeENUM;
import com.otaviowalter.stockin.model.Inventory;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.model.StockMovement;
import com.otaviowalter.stockin.repositorys.InventoryRepository;
import com.otaviowalter.stockin.repositorys.ProductsRepository;
import com.otaviowalter.stockin.repositorys.StockMovementRepository;

import jakarta.transaction.Transactional;

@Service
public class StockService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private StockMovementRepository stockMovementRepository;

	@Transactional
	public StockDTO getStock(UUID productId) {
		Products product = productsRepository.getReferenceById(productId);

		Inventory inventory = inventoryRepository.findByProduct(product);
		int quantity = (inventory != null) ? inventory.getCurrentQuantity() : 0;

		return new StockDTO(new ProductStockDTO(product), quantity);
	}

	@Transactional
	public Page<StockDTO> getAllStock(Pageable pageable) {
		return inventoryRepository.findAll(pageable)
				.map(inv -> new StockDTO(new ProductStockDTO(inv.getProduct()), inv.getCurrentQuantity()));
	}

	@Transactional
	public void registerEntry(UUID productId, int quantity, BigDecimal cost) {
		Products product = productsRepository.getReferenceById(productId);

		Inventory inventory = inventoryRepository.findByProduct(product);
		if (inventory == null) {
			inventory = new Inventory();
			inventory.setProduct(product);
			inventory.setCurrentQuantity(0);
			inventory.setMinimalQuantity(0);
		}

		inventory.setCurrentQuantity(inventory.getCurrentQuantity() + quantity);
		inventory.setLastCost(cost);
		inventory.setLastUpdate(Instant.now());
		inventoryRepository.save(inventory);

		StockMovement movement = new StockMovement();
		movement.setProduct(product);
		movement.setType(MovementTypeENUM.ENTRY);
		movement.setQuantity(quantity);
		movement.setUnitCost(cost);
		movement.setCreatedAt(Instant.now());

		stockMovementRepository.save(movement);
	}

	@Transactional
	public void registerExit(UUID productId, int quantity) {
		Products product = productsRepository.getReferenceById(productId);

		Inventory inventory = inventoryRepository.findByProduct(product);
		if (inventory == null) {
			throw new RuntimeException("No stock registered for this product");
		}

		if (inventory.getCurrentQuantity() - quantity < 0) {
			throw new RuntimeException("No product in stock");
		}

		inventory.setCurrentQuantity(inventory.getCurrentQuantity() - quantity);
		inventory.setLastUpdate(Instant.now());
		inventoryRepository.save(inventory);

		StockMovement movement = new StockMovement();
		movement.setProduct(product);
		movement.setType(MovementTypeENUM.EXIT);
		movement.setQuantity(quantity);
		movement.setUnitCost(BigDecimal.ZERO);
		movement.setCreatedAt(Instant.now());

		stockMovementRepository.save(movement);
	}
}