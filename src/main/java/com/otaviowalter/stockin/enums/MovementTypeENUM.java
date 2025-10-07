package com.otaviowalter.stockin.enums;

public enum MovementTypeENUM {
	ENTRY("entry"), EXIT("exit");

	private String movementType;

	MovementTypeENUM(String movementType) {
		this.movementType = movementType;
	}

	public String getMeasure() {
		return movementType;
	}
}