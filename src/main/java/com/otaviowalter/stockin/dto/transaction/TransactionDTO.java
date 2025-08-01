package com.otaviowalter.stockin.dto.transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.dto.purchases.PurchasesDTO;
import com.otaviowalter.stockin.dto.sales.SalesDTO;
import com.otaviowalter.stockin.dto.users.UserDTO;
import com.otaviowalter.stockin.enums.TransactionENUM;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.model.Transaction;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	private BigInteger id;

	@NotBlank(message = "there must be a type")
	private TransactionENUM type;

	private BigDecimal totalPrice;
	private PurchasesDTO purchase;

	private List<ProductsDTO> items = new ArrayList<>();

	private Date createdAt;
	private UserDTO user;
	private SalesDTO sale;

	public TransactionDTO(Transaction entity) {
		id = entity.getId();
		type = entity.getType();
		totalPrice = entity.getTotalPrice();
		purchase = new PurchasesDTO(entity.getPurchase());

		for (Products item : entity.getItems()) {
			items.add(new ProductsDTO(item));
		}

		createdAt = new Date();

		user = new UserDTO(entity.getUser());
		sale = new SalesDTO(entity.getSales());
	}

}
