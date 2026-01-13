package com.otaviowalter.stockin.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.categorys.CategorysDTO;
import com.otaviowalter.stockin.dto.categorys.CategorysMinDTO;
import com.otaviowalter.stockin.dto.products.ProductsCreateDTO;
import com.otaviowalter.stockin.dto.products.ProductsDTO;
import com.otaviowalter.stockin.dto.transaction.AdjustmentTransactionDTO;
import com.otaviowalter.stockin.enums.TransactionENUM;
import com.otaviowalter.stockin.exception.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Categorys;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.model.ProductsSnapshot;
import com.otaviowalter.stockin.model.TransactionAdjustment;
import com.otaviowalter.stockin.model.Users;
import com.otaviowalter.stockin.repositorys.CategorysRepository;
import com.otaviowalter.stockin.repositorys.ProductsRepository;
import com.otaviowalter.stockin.repositorys.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductsService {

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private CategorysRepository categorysRepository;

	@Autowired
	private TransactionService transactionService;
	
	//REMOVER DEPOIS, APENAS PARA TESTE
	@Autowired
	private UsersRepository userRepository;

	@Transactional(readOnly = true)
	public ProductsDTO findById(UUID id) {
		Products entity = productsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		return new ProductsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<ProductsDTO> findAll(Pageable pageable) {
		try {
			Page<Products> pages = productsRepository.findAll(pageable);
			return pages.map((products) -> new ProductsDTO(products));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Product Not Found");
		}
	}

	@Transactional
	public ProductsDTO create(ProductsCreateDTO newProduct) {
		Products product = new Products();
		product.setCode(newProduct.getCode());
		product.setEan(newProduct.getEan());
		product.setDescription(newProduct.getDescription());
		product.setPrice(newProduct.getPrice());
		product.setCost(newProduct.getCost());
		product.setIsFractional(newProduct.getIsFractional());
		product.setMeasure(newProduct.getMeasure());

		List<Categorys> categories = new ArrayList<>();
		for (CategorysDTO categoryDTO : newProduct.getCategory()) {
			Categorys category = categorysRepository.getReferenceById(categoryDTO.getId());
			categories.add(category);
		}
		product.setCategory(categories);

		product.setImage(newProduct.getImage());
		product.setCreatedAt(new Date());

		Products savedProduct = productsRepository.save(product);
		return new ProductsDTO(savedProduct);
	}

	@Transactional
	public ProductsDTO update(UUID id, ProductsDTO dto) {
		try {
			Products product = productsRepository.getReferenceById(id);
			ProductsSnapshot oldProduct = createProductSnapshot(product);

			product.setCode(dto.getCode());
			product.setEan(dto.getEan());
			product.setDescription(dto.getDescription());
			product.setPrice(dto.getPrice());
			product.setCost(dto.getCost());
			product.setIsFractional(dto.getIsFractional());
			product.setMeasure(dto.getMeasure());
			product.setImage(dto.getImage());

			List<Categorys> categories = new ArrayList<>();
			for (CategorysMinDTO categoryDTO : dto.getCategory()) {
				Categorys category = categorysRepository.getReferenceById(categoryDTO.getId());
				categories.add(category);
			}
			product.setCategory(categories);

			Products updatedProduct = productsRepository.save(product);

			ProductsSnapshot newProduct = createProductSnapshot(updatedProduct);

			AdjustmentTransactionDTO transactionAdjustment = createAdjustmentTransactionDTO(oldProduct, newProduct);
			transactionService.createTransactionAdjustment(transactionAdjustment);

			return new ProductsDTO(updatedProduct);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Product not found");
		}
	}

	@Transactional
	public void delete(UUID id) {
		try {
			productsRepository.deleteById(id);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Product Not Found");
		}
	}

	private AdjustmentTransactionDTO createAdjustmentTransactionDTO(ProductsSnapshot oldProduct,
			ProductsSnapshot newProduct) {
		TransactionAdjustment transactionAdjustment = new TransactionAdjustment();

		transactionAdjustment.setCreatedAt(Instant.now());
		transactionAdjustment.setType(TransactionENUM.COST_ADJUSTMENT);
		
		
		//PARA TESTE, APAGAR DEPOIS
		UUID testUserId = UUID.fromString("8108482f-6929-4ec4-a962-4531d32e4cec");
		Users user = userRepository.getReferenceById(testUserId);
		transactionAdjustment.setUser(user);
		
		transactionAdjustment.setProductBeforeAdjustment(oldProduct);
		transactionAdjustment.setProductAfterAdjustment(newProduct);

		return new AdjustmentTransactionDTO(transactionAdjustment);
	}

	private ProductsSnapshot createProductSnapshot(Products product) {
		ProductsSnapshot productSnapshot = new ProductsSnapshot();

		productSnapshot.setCreatedAt(product.getCreatedAt());
		productSnapshot.setId(product.getId());
		productSnapshot.setCode(product.getCode());
		productSnapshot.setEan(product.getEan());
		productSnapshot.setDescription(product.getDescription());
		productSnapshot.setPrice(product.getPrice());
		productSnapshot.setCost(product.getCost());
		productSnapshot.setIsFractional(product.getIsFractional());
		productSnapshot.setMeasure(product.getMeasure());
		productSnapshot.setImage(product.getImage());
		productSnapshot
				.setCategorys(product.getCategory().stream().map(Categorys::getName).collect(Collectors.joining(";")));

		return productSnapshot;

	}

}
