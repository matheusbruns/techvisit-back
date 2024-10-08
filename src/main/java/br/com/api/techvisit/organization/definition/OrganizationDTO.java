package br.com.api.techvisit.organization.definition;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationDTO {

	private Long id;

	@NotNull
	private String externalCode;

	@NotNull
	private String name;

	private LocalDate creationDate;

	private LocalDate expirationDate;

}
