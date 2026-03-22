package com.otaviowalter.stockin.dto.users;

import java.util.Date;
import java.util.UUID;

import com.otaviowalter.stockin.enums.UserRoleENUM;
import com.otaviowalter.stockin.model.Users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

	private UUID id;

	@NotBlank(message = "name is required")
	private String name;

	@NotBlank(message = "email is required")
	@Email(message = "must be an email")
	private String email;

	@NotBlank(message = "password is required")
	private String password;

	@NotNull(message = "role is required")
	@Enumerated(EnumType.STRING)
	private UserRoleENUM role;

	private Date adminition;

	public UserRegisterDTO(Users entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		password = entity.getPassword();
		role = entity.getRole();
		adminition = entity.getAdminition();
	}

}
