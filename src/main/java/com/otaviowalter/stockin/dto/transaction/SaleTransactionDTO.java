package com.otaviowalter.stockin.dto.transaction;

import com.otaviowalter.stockin.dto.sales.SalesDTO;
import com.otaviowalter.stockin.model.TransactionSale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleTransactionDTO extends TransactionDTO {

	private SalesDTO sale;

	public SaleTransactionDTO(TransactionSale entity) {
		super(entity);
		this.sale = new SalesDTO(entity.getSales());
	}
}
