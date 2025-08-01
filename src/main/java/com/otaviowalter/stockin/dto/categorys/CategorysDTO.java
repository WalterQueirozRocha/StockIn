package com.otaviowalter.stockin.dto.categorys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.model.Categorys;
import com.otaviowalter.stockin.model.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategorysDTO {

	private UUID id;
	private String name;

	private List<ProductsDTO> products = new ArrayList<>();
	private String image;
	private Date createdAt;

	public CategorysDTO(Categorys entity) {
		id = entity.getId();
		name = entity.getName();

		for (Products product : entity.getProducts()) {
			products.add(new ProductsDTO(product));
		}

		image = entity.getImage();
		createdAt = entity.getCreatedAt();

	}

}
