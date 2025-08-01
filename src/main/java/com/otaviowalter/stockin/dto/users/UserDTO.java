package com.otaviowalter.stockin.dto.users;

import java.util.Date;
import java.util.UUID;

import com.otaviowalter.stockin.enums.UserRoleENUM;
import com.otaviowalter.stockin.model.Users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private UUID id;

	@NotBlank(message = "name is required")
	private String name;

	@NotBlank(message = "email is required")
	@Email(message = "must be an email")
	private String email;

	@Enumerated(EnumType.STRING)
	private UserRoleENUM role;

	private Date adminition;

	public UserDTO(Users entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		role = entity.getRole();
		adminition = entity.getAdminition();
	}
}
