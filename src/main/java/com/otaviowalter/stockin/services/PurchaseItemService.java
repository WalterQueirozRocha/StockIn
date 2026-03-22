package com.otaviowalter.stockin.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otaviowalter.stockin.dto.purchasesitems.PurchaseItemsDTO;
import com.otaviowalter.stockin.exception.exceptions.ResourceNotFoundException;
import com.otaviowalter.stockin.model.Products;
import com.otaviowalter.stockin.model.PurchaseItems;
import com.otaviowalter.stockin.repositorys.ProductsRepository;
import com.otaviowalter.stockin.repositorys.PurchaseItemsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PurchaseItemService {

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Transactional(readOnly = true)
    public PurchaseItemsDTO findById(UUID id) {
        PurchaseItems entity = purchaseItemsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item item not found"));
        return new PurchaseItemsDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<PurchaseItemsDTO> findAll(Pageable pageable) {
        Page<PurchaseItems> pages = purchaseItemsRepository.findAll(pageable);
        return pages.map(PurchaseItemsDTO::new);
    }

    @Transactional
    public PurchaseItemsDTO create(PurchaseItemsDTO dto) {
        PurchaseItems item = new PurchaseItems();

        Products product = productsRepository.getReferenceById(dto.getProduct().getId());
        item.setProduct(product);

        item.setDescription(product.getDescription());
        item.setQuantity(dto.getQuantity());
        item.setSubTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
        item.setSubTotalCost(product.getCost().multiply(BigDecimal.valueOf(dto.getQuantity())));
        item.setCreatedAt(new Date());

        PurchaseItems saved = purchaseItemsRepository.save(item);
        return new PurchaseItemsDTO(saved);
    }

    @Transactional
    public PurchaseItemsDTO update(UUID id, PurchaseItemsDTO dto) {
        try {
            PurchaseItems entity = purchaseItemsRepository.getReferenceById(id);

            Products product = productsRepository.getReferenceById(dto.getProduct().getId());
            entity.setProduct(product);

            entity.setDescription(dto.getDescription());
            entity.setQuantity(dto.getQuantity());
            entity.setSubTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
            entity.setSubTotalCost(product.getCost().multiply(BigDecimal.valueOf(dto.getQuantity())));
            entity.setCreatedAt(new Date());

            return new PurchaseItemsDTO(purchaseItemsRepository.save(entity));

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Item item not found");
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!purchaseItemsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item item not found");
        }
        purchaseItemsRepository.deleteById(id);
    }
}
