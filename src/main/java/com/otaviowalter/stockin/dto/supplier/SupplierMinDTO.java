package com.otaviowalter.stockin.dto.supplier;

import java.util.UUID;

import com.otaviowalter.stockin.model.Supplier;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierMinDTO {

	private UUID id;
	@NotBlank(message = "name is required")
	private String name;

	public SupplierMinDTO(Supplier entity) {
		id = entity.getId();
		name = entity.getName();
	}
}
