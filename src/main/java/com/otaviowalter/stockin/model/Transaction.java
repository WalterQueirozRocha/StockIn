package com.otaviowalter.stockin.model;import java.math.BigInteger;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.otaviowalter.stockin.enums.TransactionENUM;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "transactional")
@Table(name = "tb_transactional")

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,       
        include = JsonTypeInfo.As.PROPERTY,
        property = "transactionType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TransactionSale.class, name = "SALE"),
    @JsonSubTypes.Type(value = TransactionPurchase.class, name = "PURCHASE"),
    @JsonSubTypes.Type(value = TransactionDevolution.class, name = "DEVOLUTION"),
    @JsonSubTypes.Type(value = TransactionAdjustment.class, name = "ADJUSTMENT")
})
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;

	private Instant createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	private TransactionENUM type;
}
