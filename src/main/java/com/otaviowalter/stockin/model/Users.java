package com.otaviowalter.stockin.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.otaviowalter.stockin.enums.UserRoleENUM;

import jakarta.persistence.Column;
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
public class Users implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRoleENUM role;

	private Date adminition;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRoleENUM.ADMINISTRATOR)
			return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"),
					new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
	}

	@Override
	public String getUsername() {
		return email;
	}
}
