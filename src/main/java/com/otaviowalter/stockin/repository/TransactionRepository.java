package com.otaviowalter.stockin.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otaviowalter.stockin.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, BigInteger>{

}
