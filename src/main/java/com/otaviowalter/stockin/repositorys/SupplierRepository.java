package com.otaviowalter.stockin.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, UUID>{

}
