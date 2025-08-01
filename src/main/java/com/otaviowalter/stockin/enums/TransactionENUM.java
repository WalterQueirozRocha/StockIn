package com.otaviowalter.stockin.enums;

public enum TransactionENUM {
	PURCHASE("purchase"), INVENTORY_REDUCTION("inventory_reduction"), INVENTORY_ADDITION("inventory_addition"), COST_ADJUSTMENT("cost_adjustment"), DEVOLUTION("devolution");
	
	private String transaction;

	TransactionENUM(String transaction) {
		this.transaction = transaction;
	}

	public String getMeasure() {
		return transaction;
	}
}
