package com.otaviowalter.stockin.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "before_product_id")),
			@AttributeOverride(name = "code", column = @Column(name = "before_product_code")),
			@AttributeOverride(name = "ean", column = @Column(name = "before_product_ean")),
			@AttributeOverride(name = "description", column = @Column(name = "before_product_description")),
			@AttributeOverride(name = "price", column = @Column(name = "before_product_price")),
			@AttributeOverride(name = "cost", column = @Column(name = "before_product_cost")),
			@AttributeOverride(name = "isFractional", column = @Column(name = "before_product_fractional")),
			@AttributeOverride(name = "measure", column = @Column(name = "before_product_measure")),
			@AttributeOverride(name = "categorys", column = @Column(name = "before_product_category")),
			@AttributeOverride(name = "image", column = @Column(name = "before_product_image")),
			@AttributeOverride(name = "createdAt", column = @Column(name = "before_product_created_at")) })
	private ProductsSnapshot productBeforeAdjustment;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "after_product_id")),
			@AttributeOverride(name = "code", column = @Column(name = "after_product_code")),
			@AttributeOverride(name = "ean", column = @Column(name = "after_product_ean")),
			@AttributeOverride(name = "description", column = @Column(name = "after_product_description")),
			@AttributeOverride(name = "price", column = @Column(name = "after_product_price")),
			@AttributeOverride(name = "cost", column = @Column(name = "after_product_cost")),
			@AttributeOverride(name = "isFractional", column = @Column(name = "after_product_fractional")),
			@AttributeOverride(name = "measure", column = @Column(name = "after_product_measure")),
			@AttributeOverride(name = "categorys", column = @Column(name = "after_product_category")),
			@AttributeOverride(name = "image", column = @Column(name = "after_product_image")),
			@AttributeOverride(name = "createdAt", column = @Column(name = "after_product_created_at")) })
	private ProductsSnapshot productAfterAdjustment;

}