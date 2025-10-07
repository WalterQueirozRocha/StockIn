package com.otaviowalter.stockin.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {}