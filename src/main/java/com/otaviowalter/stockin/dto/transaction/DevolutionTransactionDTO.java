package com.otaviowalter.stockin.dto.transaction;

import com.otaviowalter.stockin.dto.devolutions.DevolutionDTO;
import com.otaviowalter.stockin.model.TransactionDevolution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DevolutionTransactionDTO extends TransactionDTO {

	private DevolutionDTO devolution;

	public DevolutionTransactionDTO(TransactionDevolution entity) {
		super(entity);
		this.devolution = new DevolutionDTO(entity.getDevolution());
	}
}
