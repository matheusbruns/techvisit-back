package br.com.api.techvisit.organization.definition;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationDTO {

	private Long id;

	@NotNull
	private String externalCode;

	@NotNull
	private String name;

	@JsonIgnore
	private LocalDate creationDate;

	@JsonIgnore
	private LocalDate expirationDate;

}
