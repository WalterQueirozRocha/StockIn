package com.otaviowalter.stockin.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.supplier.SupplierDTO;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Supplier;
import com.otaviowalter.stockin.repositorys.SupplierRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepository;

	@Transactional(readOnly = true)
	public SupplierDTO findById(UUID id) {
		Supplier entity = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supllier Not Found"));
		return new SupplierDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SupplierDTO> findAll(Pageable pageable) {
		try {
			Page<Supplier> pages = supplierRepository.findAll(pageable);
			return pages.map((supplier) -> new SupplierDTO(supplier));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Supllier Not Found");
		}
	}

	@Transactional
	public SupplierDTO create(SupplierDTO newSupplier) {
		Supplier supplier = new Supplier();
		supplier.setName(newSupplier.getName());
		supplier.setCreatedAt(new Date());
		

		Supplier savedSupplier = supplierRepository.save(supplier);
		return new SupplierDTO(savedSupplier);
	}

	@Transactional
	public SupplierDTO update(UUID id, SupplierDTO dto) {
		try {
			Supplier entity = supplierRepository.getReferenceById(id);
			entity.setName(dto.getName());
			return new SupplierDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Supllier Not Found");
		}
	}

	@Transactional
	public void delete(UUID id) {
		try {
			supplierRepository.deleteById(id);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Supllier Not Found");
		}
	}

}
