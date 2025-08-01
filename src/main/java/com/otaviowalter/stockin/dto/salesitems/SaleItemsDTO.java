package com.otaviowalter.stockin.dto.salesitems;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.model.SaleItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemsDTO {

	private UUID id;
	private List<ProductsDTO> products = new ArrayList<>();
	private int quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private Instant createdAt;

	public SaleItemsDTO(SaleItems entity) {
		id = entity.getId();

		for (Products product : entity.getProducts()) {
			products.add(new ProductsDTO(product));
		}

		quantity = entity.getQuantity();
		unitPrice = entity.getTotalPrice();
		totalPrice = entity.getTotalPrice();
		createdAt = entity.getCreatedAt();
	}

}
