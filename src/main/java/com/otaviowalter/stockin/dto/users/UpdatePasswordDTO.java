package com.otaviowalter.stockin.dto.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDTO {
	@NotBlank(message = "new password is required")
	private String newPassword;
	@NotBlank(message = "password is required")
	private String oldPassword;
}
