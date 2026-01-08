package com.otaviowalter.stockin.dto.transaction;

import java.math.BigInteger;
import java.time.Instant;

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

	private Instant createdAt;
	private UserDTO user;

	public TransactionDTO(Transaction entity) {
		id = entity.getId();
		createdAt = entity.getCreatedAt();
		user = new UserDTO(entity.getUser());
		type = entity.getType();
	}

}
