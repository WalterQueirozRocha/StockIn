package com.otaviowalter.stockin.enums;

public enum MeasureENUM {
 KG("kg"), LATA("lata"), METRO("metro"), PCT("pct"), PEÇA("peça"), UNIDADE("unidade");
	private String measure;

	MeasureENUM(String measure) {
		this.measure = measure;
	}

	public String getMeasure() {
		return measure;
	}
}
