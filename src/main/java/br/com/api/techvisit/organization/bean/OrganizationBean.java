package br.com.api.techvisit.organization.bean;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationBean {

	private Long id;

	@NotNull
	private String externalCode;

	@NotNull
	private String name;

	private LocalDate expirationDate;

}