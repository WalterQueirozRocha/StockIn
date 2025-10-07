package com.otaviowalter.stockin.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.enums.MeasureENUM;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Entity(name = "products")
@Table(name = "tb_products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String code;
	private String ean;
	private String description;
	private BigDecimal price;
	private BigDecimal cost;
	private Boolean isFractional;
	
	@Enumerated(EnumType.STRING)
	private MeasureENUM measure;

	@ManyToMany
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Categorys> category = new ArrayList<>();
	
	private String image;
	private Date createdAt;

}
