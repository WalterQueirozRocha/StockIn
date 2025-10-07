package com.otaviowalter.stockin.dto.salesitems;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.model.SaleItems;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemsDTO {

	private UUID id;
	@NotNull
	private ProductsDTO product;
	@NotNull
	private int quantity;
	private String description;
	private BigDecimal subTotalPrice;
	private Instant createdAt;

	public SaleItemsDTO(SaleItems entity) {
		id = entity.getId();
		product = new ProductsDTO(entity.getProduct()); 
		subTotalPrice = entity.getSubTotalPrice();
		quantity = entity.getQuantity();
		description = entity.getDescription();
		createdAt = entity.getCreatedAt();
	}

}
