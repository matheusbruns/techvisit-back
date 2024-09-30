package br.com.api.techvisit.user.definition;

import lombok.Getter;

@Getter
public enum UserRole {

	ADMIN("admin"), USER("user"), TECHNICIAN("technician");

	private String role;

	UserRole(String role) {
		this.role = role;
	}

}
