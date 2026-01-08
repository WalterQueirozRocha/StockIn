package com.otaviowalter.stockin.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Entity(name = "transactional_sale")
@JsonTypeName("SALE")
public class TransactionSale extends Transaction{

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sales_id")
	private Sales sales;

}
