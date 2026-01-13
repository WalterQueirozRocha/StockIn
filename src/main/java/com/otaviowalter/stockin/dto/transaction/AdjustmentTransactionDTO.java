package com.otaviowalter.stockin.dto.transaction;

import com.otaviowalter.stockin.dto.products.ProductsSnapshotDTO;
import com.otaviowalter.stockin.model.TransactionAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentTransactionDTO extends TransactionDTO {

	private ProductsSnapshotDTO productBeforeAdjustment;
	private ProductsSnapshotDTO productAfterAdjustment;

	public AdjustmentTransactionDTO(TransactionAdjustment entity) {
		super(entity);
		this.productBeforeAdjustment = new ProductsSnapshotDTO(entity.getProductBeforeAdjustment());

		this.productAfterAdjustment = new ProductsSnapshotDTO(entity.getProductAfterAdjustment());
	}
}
