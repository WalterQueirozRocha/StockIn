package com.otaviowalter.stockin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactional_purchase")
public class TransactionPurchase extends Transaction{
	
	@OneToOne
	@JoinColumn(name = "purchase_id")
	private Purchases purchase;
}
