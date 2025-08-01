package com.otaviowalter.stockin.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "sales")
@Table(name = "tb_sales")
public class Sales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private List<SaleItems> productList = new ArrayList<>();
	private BigDecimal subTotal;
	private BigDecimal discountPercentage;
	private BigDecimal total;
	private Users user;
	private Instant createdAt;
}
