package com.otaviowalter.stockin.dto.products;

import java.math.BigDecimal;
import java.util.Date;

import com.otaviowalter.stockin.enums.MeasureENUM;
import com.otaviowalter.stockin.model.ProductsSnapshot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsSnapshotDTO {

	private String code;
	private String ean;
	private String description;
	private BigDecimal price;
	private BigDecimal cost;
	private Boolean isFractional;
	private MeasureENUM measure;
	private String categorySnapshot;
	private String image;

	public ProductsSnapshotDTO(ProductsSnapshot snapshot) {
		this.code = snapshot.getCode();
		this.ean = snapshot.getEan();
		this.description = snapshot.getDescription();
		this.price = snapshot.getPrice();
		this.cost = snapshot.getCost();
		this.isFractional = snapshot.getIsFractional();
		this.measure = snapshot.getMeasure();
		this.categorySnapshot = snapshot.getCategorys();
		this.image = snapshot.getImage();
	}

	public ProductsSnapshot toEntity() {
		ProductsSnapshot snapshot = new ProductsSnapshot();
		snapshot.setCode(this.code);
		snapshot.setEan(this.ean);
		snapshot.setDescription(this.description);
		snapshot.setPrice(this.price);
		snapshot.setCost(this.cost);
		snapshot.setIsFractional(this.isFractional);
		snapshot.setMeasure(this.measure);
		snapshot.setCategorys(this.categorySnapshot);
		snapshot.setImage(this.image);
		snapshot.setCreatedAt(new Date());
		return snapshot;
	}
}
