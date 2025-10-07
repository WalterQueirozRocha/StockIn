package com.otaviowalter.stockin.dto.categorys;

import java.util.UUID;

import com.otaviowalter.stockin.model.Categorys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategorysMinDTO {

	private UUID id;
	private String name;

	public CategorysMinDTO(Categorys entity) {
		id = entity.getId();
		name = entity.getName();
	}

}
