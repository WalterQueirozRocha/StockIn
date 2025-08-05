package com.otaviowalter.stockin.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.Purchases;

public interface PurchasesRepository extends JpaRepository<Purchases, UUID>{

}
