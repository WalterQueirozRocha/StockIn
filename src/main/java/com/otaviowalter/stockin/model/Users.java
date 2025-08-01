package com.otaviowalter.stockin.model;

import java.util.Date;
import java.util.UUID;

import com.otaviowalter.stockin.enums.UserRoleENUM;

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
@Entity(name = "user")
@Table(name = "tb_user")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRoleENUM role;
	
	private Date adminition;
}
