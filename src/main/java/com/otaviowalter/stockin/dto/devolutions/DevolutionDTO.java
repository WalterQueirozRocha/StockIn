package com.otaviowalter.stockin.dto.devolutions;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.devolutionitems.DevolutionItemsDTO;
import com.otaviowalter.stockin.dto.sales.SalesDTO;
import com.otaviowalter.stockin.dto.users.UserDTO;
import com.otaviowalter.stockin.model.Devolution;
import com.otaviowalter.stockin.model.DevolutionItems;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DevolutionDTO {

	private UUID id;
	@NotEmpty(message = "there must be products")
	private List<DevolutionItemsDTO> productList = new ArrayList<>();
	private BigDecimal total;
	private UserDTO user;
	private Instant createdAt;
	@NotNull(message = "there must be Sale")
	private SalesDTO sale;

	public DevolutionDTO(Devolution entity) {
		id = entity.getId();
		for (DevolutionItems products : entity.getProductList()) {
			productList.add(new DevolutionItemsDTO(products));
		}
		total = entity.getTotal();
		user = new UserDTO(entity.getUser());
		sale = new SalesDTO(entity.getSales());
		createdAt = entity.getCreatedAt();

	}

}
