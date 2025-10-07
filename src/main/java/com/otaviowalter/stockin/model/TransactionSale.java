package com.otaviowalter.stockin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "transactional_sale")
public class TransactionSale extends Transaction{

	@OneToOne
	@JoinColumn(name = "sales_id")
	private Sales sales;

}
