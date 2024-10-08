package br.com.api.techvisit.user.definition;

import lombok.Getter;

@Getter
public enum UserRole {

	ADMIN("ADMIN"), USER("USER"), TECHNICIAN("TECHNICIAN");

	private String role;

	UserRole(String role) {
		this.role = role;
	}

}
