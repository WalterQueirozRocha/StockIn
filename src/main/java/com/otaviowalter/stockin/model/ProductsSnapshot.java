package com.otaviowalter.stockin.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.otaviowalter.stockin.enums.MeasureENUM;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Embeddable
public class ProductsSnapshot {

	private UUID id;
	private String code;
	private String ean;
	private String description;
	private BigDecimal price;
	private BigDecimal cost;
	private Boolean isFractional;

	@Enumerated(EnumType.STRING)
	private MeasureENUM measure;

	private String categorys;

	private String image;
	private Date createdAt;

}
