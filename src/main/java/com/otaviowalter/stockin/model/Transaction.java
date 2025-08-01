package com.otaviowalter.stockin.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.otaviowalter.stockin.enums.TransactionENUM;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "transactional")
@Table(name = "tb_transactional")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private BigInteger id;

	@Enumerated(EnumType.STRING)
	private TransactionENUM type;

	private BigDecimal totalPrice;
	private Purchases purchase;

	private List<Products> items = new ArrayList<>();

	private Date createdAt;
	private Users user;
	private Sales sales;

}
