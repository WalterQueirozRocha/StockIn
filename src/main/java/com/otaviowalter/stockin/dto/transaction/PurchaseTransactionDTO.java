package com.otaviowalter.stockin.dto.transaction;

import com.otaviowalter.stockin.dto.purchases.PurchasesDTO;
import com.otaviowalter.stockin.model.TransactionPurchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseTransactionDTO extends TransactionDTO {

	private PurchasesDTO purchase;

	public PurchaseTransactionDTO(TransactionPurchase entity) {
		super(entity);
		this.purchase = new PurchasesDTO(entity.getPurchase());
	}
}