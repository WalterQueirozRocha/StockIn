package com.otaviowalter.stockin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity(name = "transactional")
@Table(name = "tb_transactional")
public class TransactionPurchase extends Transaction{
	
	@OneToOne
	@JoinColumn(name = "purchase_id")
	private Purchases purchase;
}
