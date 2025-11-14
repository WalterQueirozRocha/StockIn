package com.otaviowalter.stockin.dto.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.model.TransactionAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentTransactionDTO extends TransactionDTO {

	private List<ProductsDTO> items = new ArrayList<>();

	public AdjustmentTransactionDTO(TransactionAdjustment entity) {
		super(entity);
		this.items = entity.getItems().stream().map(ProductsDTO::new).collect(Collectors.toList());
	}
}
