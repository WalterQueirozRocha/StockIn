package com.otaviowalter.stockin.dto.purchasesitems;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.otaviowalter.stockin.model.PurchaseItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemsDTO {
	
	private UUID id;
	private String code;
	private String description;
	private int quantity;
	private BigDecimal price;
	private BigDecimal cost;
	private Date createdAt;
	
	public PurchaseItemsDTO(PurchaseItems entity) {
		id = entity.getId();
		code = entity.getCode();
		description = entity.getDescription();
		quantity = entity.getQuantity();
		price = entity.getPrice();
		cost = entity.getCost();
		createdAt = entity.getCreatedAt();
	}
}
