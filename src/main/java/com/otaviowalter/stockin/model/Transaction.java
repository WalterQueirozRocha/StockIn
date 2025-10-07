package com.otaviowalter.stockin.model;import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;

	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
}
