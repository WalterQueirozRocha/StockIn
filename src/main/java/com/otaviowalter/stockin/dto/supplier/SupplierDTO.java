package com.otaviowalter.stockin.dto.supplier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.purchases.PurchasesMinDTO;
import com.otaviowalter.stockin.model.Purchases;
import com.otaviowalter.stockin.model.Supplier;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

	private UUID id;
	@NotBlank(message = "name is required")
	private String name;
	private List<PurchasesMinDTO> purchases = new ArrayList<>();
	private Date createdAt;

	public SupplierDTO(Supplier entity) {
		id = entity.getId();
		name = entity.getName();

		for (Purchases purchase : entity.getPurchases()) {
			purchases.add(new PurchasesMinDTO(purchase));
		}

		createdAt = entity.getCreatedAt();
	}
}
