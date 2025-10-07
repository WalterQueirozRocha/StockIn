package com.otaviowalter.stockin.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.Categorys;

public interface CategorysRepository extends JpaRepository<Categorys, UUID>{

}
