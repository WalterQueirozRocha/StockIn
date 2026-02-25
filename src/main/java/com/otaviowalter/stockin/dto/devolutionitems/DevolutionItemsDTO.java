package com.otaviowalter.stockin.dto.devolutionitems;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.model.DevolutionItems;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DevolutionItemsDTO {

	private UUID id;
	@NotNull
	private ProductsDTO product;
	@NotNull
	private int quantity;
	private String description;
	private BigDecimal subTotalPrice;
	private Instant createdAt;
	private Boolean isResaleable;

	public DevolutionItemsDTO(DevolutionItems entity) {
		id = entity.getId();
		product = new ProductsDTO(entity.getProduct());
		subTotalPrice = entity.getSubTotalPrice();
		quantity = entity.getQuantity();
		description = entity.getDescription();
		createdAt = entity.getCreatedAt();
		isResaleable = entity.getIsResaleable();
	}

}
