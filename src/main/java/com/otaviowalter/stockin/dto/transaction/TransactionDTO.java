package com.otaviowalter.stockin.dto.transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.otaviowalter.stockin.dto.users.UserDTO;
import com.otaviowalter.stockin.enums.TransactionENUM;
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
	private Date createdAt;
	private UserDTO user;

	public TransactionDTO(Transaction entity) {
		id = entity.getId();
		type = entity.getType();
		totalPrice = entity.getTotalPrice();
		createdAt = new Date();
		user = new UserDTO(entity.getUser());
	}

}
