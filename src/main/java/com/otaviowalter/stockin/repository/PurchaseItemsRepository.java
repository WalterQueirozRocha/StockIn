package com.otaviowalter.stockin.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.PurchaseItems;

public interface PurchaseItemsRepository extends JpaRepository<PurchaseItems, UUID>{

}
