package com.otaviowalter.stockin.dto.transaction;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.model.TransactionAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentTransactionDTO extends TransactionDTO {

	private ProductsDTO item;

	public AdjustmentTransactionDTO(TransactionAdjustment entity) {
		super(entity);
		this.item = new ProductsDTO(entity.getItem());
	}
}
