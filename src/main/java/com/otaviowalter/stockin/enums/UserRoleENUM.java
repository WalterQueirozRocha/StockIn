package com.otaviowalter.stockin.enums;

public enum UserRoleENUM {
	EMPLOYEE("employee"), ADMINISTRATOR("administrator");

	private String role;

	UserRoleENUM(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
