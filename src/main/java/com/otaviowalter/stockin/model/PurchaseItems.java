package com.otaviowalter.stockin.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity(name = "purchasesItems")
@Table(name = "tb_purchases_items")
public class PurchaseItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
	private Products product;
	private int quantity;
	private String description;
	private BigDecimal subTotalPrice;
	private BigDecimal subTotalCost;
	private Date createdAt;

}
