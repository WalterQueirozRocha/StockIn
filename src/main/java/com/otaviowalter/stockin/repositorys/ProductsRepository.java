package com.otaviowalter.stockin.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.Products;

public interface ProductsRepository extends JpaRepository<Products, UUID>{

}
