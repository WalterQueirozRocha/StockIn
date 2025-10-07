package com.otaviowalter.stockin.dto.purchasesitems;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.model.PurchaseItems;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemsDTO {
	
	private UUID id;
	@NotNull
	private ProductsDTO product;
	@NotNull
	private int quantity;
	private String description;
	private BigDecimal subTotalPrice;
	private BigDecimal subTotalCost;
	private Date createdAt;
	
	public PurchaseItemsDTO(PurchaseItems entity) {
		id = entity.getId();
		product = new ProductsDTO(entity.getProduct());
		description = entity.getDescription();
		quantity = entity.getQuantity();
		createdAt = entity.getCreatedAt();
		subTotalPrice = entity.getSubTotalPrice();
		subTotalCost = entity.getSubTotalCost();
	}
}
