package com.otaviowalter.stockin.dto.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationDTO {

	@NotBlank(message = "email is required")
	private String email;

	@NotBlank(message = "password is required")
	private String password;
}
