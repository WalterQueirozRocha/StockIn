package com.otaviowalter.stockin.dto.purchases;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.purchasesitems.PurchaseItemsDTO;
import com.otaviowalter.stockin.dto.supplier.SupplierMinDTO;
import com.otaviowalter.stockin.dto.users.UserDTO;
import com.otaviowalter.stockin.model.PurchaseItems;
import com.otaviowalter.stockin.model.Purchases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchasesDTO {

	private UUID id;
	private List<PurchaseItemsDTO> itemsList = new ArrayList<>();
	private BigDecimal totalCost;
	private BigDecimal totalPrice;
	private BigDecimal discountPercentage;
	private String observation;
	private Instant createdAt;
	private SupplierMinDTO supplier;
	private UserDTO user;

	public PurchasesDTO(Purchases entity) {
		id = entity.getId();

		for (PurchaseItems item : entity.getItemsList()) {
			itemsList.add(new PurchaseItemsDTO(item));
		}
		totalPrice = entity.getTotalPrice();
		totalCost = entity.getTotalCost();
		discountPercentage = entity.getDiscountPercentage();
		observation = entity.getObservation();
		createdAt = entity.getCreatedAt();
		supplier = new SupplierMinDTO(entity.getSupplier());
		user = new UserDTO(entity.getUser());
		
	}

}
