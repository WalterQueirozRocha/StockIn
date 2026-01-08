package com.otaviowalter.stockin.repositorys;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.TransactionSale;

public interface SaleTransactionRepository extends JpaRepository<TransactionSale, BigInteger> {

}
