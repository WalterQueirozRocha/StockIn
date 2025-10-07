package com.otaviowalter.stockin.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.categorys.CategorysDTO;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Categorys;
import com.otaviowalter.stockin.repositorys.CategorysRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategorysRepository categorysRepository;

	@Transactional(readOnly = true)
	public CategorysDTO findById(UUID id) {
		Categorys entity = categorysRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		return new CategorysDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<CategorysDTO> findAll(Pageable pageable) {
		try {
			Page<Categorys> pages = categorysRepository.findAll(pageable);
			return pages.map((categorys) -> new CategorysDTO(categorys));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("User Not Found");
		}
	}


	@Transactional
	public CategorysDTO create(CategorysDTO dto) {
		Categorys category = new Categorys();
		category.setName(dto.getName());
		category.setImage(dto.getImage());
		category.setCreatedAt(new Date());

		Categorys saved = categorysRepository.save(category);
		return new CategorysDTO(saved);
	}

	@Transactional
	public CategorysDTO update(UUID id, CategorysDTO dto) {
		try {
			Categorys entity = categorysRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity.setImage(dto.getImage());

			return new CategorysDTO(categorysRepository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Category not found");
		}
	}

	@Transactional
	public void delete(UUID id) {
		if (!categorysRepository.existsById(id)) {
			throw new ResourceNotFoundException("Category not found");
		}
		categorysRepository.deleteById(id);
	}
}
