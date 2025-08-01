package com.otaviowalter.stockin.dto.products;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.categorys.CategorysDTO;
import com.otaviowalter.stockin.enums.MeasureENUM;
import com.otaviowalter.stockin.model.Categorys;
import com.otaviowalter.stockin.model.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {

	private UUID id;
	private String code;
	private String ean;
	private String description;
	private int quantity;
	private int minimalQuantity;
	private BigDecimal price;
	private BigDecimal cost;
	private Boolean isFractional;
	private MeasureENUM measure;
	private List<CategorysDTO> category = new ArrayList<>();
	private String image;
	private Date createdAt;

	public ProductsDTO(Products entity) {
		id = entity.getId();
		code = entity.getCode();
		ean = entity.getEan();
		description = entity.getDescription();
		quantity = entity.getQuantity();
		minimalQuantity = entity.getMinimalQuantity();
		price = entity.getPrice();
		cost = entity.getCost();
		isFractional = entity.getIsFractional();
		measure = entity.getMeasure();

		for (Categorys categorys : entity.getCategory()) {
			category.add(new CategorysDTO(categorys));
		}

		image = entity.getImage();
		createdAt = entity.getCreatedAt();

	}

}
