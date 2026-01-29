package com.otaviowalter.stockin.enums;

public enum TransactionENUM {
	PURCHASE("purchase"), SALE("sale"), ADJUSTMENT("adjustment"), DEVOLUTION("devolution");
	
	private String transaction;

	TransactionENUM(String transaction) {
		this.transaction = transaction;
	}

	public String getMeasure() {
		return transaction;
	}
}
