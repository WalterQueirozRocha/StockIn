package com.otaviowalter.stockin.dto.purchases;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.purchasesitems.PurchaseItemsDTO;
import com.otaviowalter.stockin.model.PurchaseItems;
import com.otaviowalter.stockin.model.Purchases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchasesMinDTO {

	private UUID id;
	private List<PurchaseItemsDTO> itemsList = new ArrayList<>();
	private BigDecimal totalCost;
	private String observation;
	private Instant createdAt;

	public PurchasesMinDTO(Purchases entity) {
		id = entity.getId();

		for (PurchaseItems item : entity.getItemsList()) {
			itemsList.add(new PurchaseItemsDTO(item));
		}
		totalCost = entity.getTotalCost();
		observation = entity.getObservation();
		createdAt = entity.getCreatedAt();
	}

}
