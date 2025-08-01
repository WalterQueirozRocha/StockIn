package com.otaviowalter.stockin.dto.sales;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.salesitems.SaleItemsDTO;
import com.otaviowalter.stockin.dto.users.UserDTO;
import com.otaviowalter.stockin.model.SaleItems;
import com.otaviowalter.stockin.model.Sales;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {

	private UUID id;
	@NotBlank(message = "there must be products")
	private List<SaleItemsDTO> productList = new ArrayList<>();
	private BigDecimal subTotal;
	private BigDecimal discountPercentage;
	private BigDecimal total;
	private UserDTO user;
	private Instant createdAt;

	public SalesDTO(Sales entity) {
		id = entity.getId();

		for (SaleItems products : entity.getProductList()) {
			productList.add(new SaleItemsDTO(products));
		}

		subTotal = entity.getSubTotal();
		discountPercentage = entity.getDiscountPercentage();
		total = entity.getTotal();
		user = new UserDTO(entity.getUser());
		createdAt = entity.getCreatedAt();
	}

}
