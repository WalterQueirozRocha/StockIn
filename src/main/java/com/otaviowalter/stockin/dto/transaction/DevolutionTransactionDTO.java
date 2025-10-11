package com.otaviowalter.stockin.dto.transaction;

import java.util.List;
import java.util.stream.Collectors;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.dto.sales.SalesDTO;
import com.otaviowalter.stockin.model.TransactionDevolution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DevolutionTransactionDTO extends TransactionDTO {

	private List<ProductsDTO> items;
	private SalesDTO sales;

	public DevolutionTransactionDTO(TransactionDevolution entity) {
		super(entity);
		this.items = entity.getItems().stream().map(ProductsDTO::new).collect(Collectors.toList());
		this.sales = new SalesDTO(entity.getSales());
	}
}
