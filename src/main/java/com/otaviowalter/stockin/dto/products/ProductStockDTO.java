package com.otaviowalter.stockin.dto.products;

import java.math.BigDecimal;
import java.util.UUID;

import com.otaviowalter.stockin.enums.MeasureENUM;
import com.otaviowalter.stockin.model.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDTO {

	private UUID id;
	private String code;
	private String ean;
	private String description;
	private BigDecimal price;
	private MeasureENUM measure;
	private String image;

	public ProductStockDTO(Products entity) {
		id = entity.getId();
		code = entity.getCode();
		ean = entity.getEan();
		description = entity.getDescription();
		price = entity.getPrice();
		measure = entity.getMeasure();
	}
}
