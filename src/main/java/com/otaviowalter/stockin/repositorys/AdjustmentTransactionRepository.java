package com.otaviowalter.stockin.repositorys;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.TransactionAdjustment;

public interface AdjustmentTransactionRepository extends JpaRepository<TransactionAdjustment, BigInteger>{

}
