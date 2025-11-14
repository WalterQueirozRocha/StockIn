package com.otaviowalter.stockin.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactional_cost_adjustment")
public class TransactionAdjustment extends Transaction {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_transaction_products", joinColumns = @JoinColumn(name = "transaction_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Products> items = new ArrayList<>();
}
 