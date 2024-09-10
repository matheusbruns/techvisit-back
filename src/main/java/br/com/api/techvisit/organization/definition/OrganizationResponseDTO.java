package br.com.api.techvisit.organization.definition;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationResponseDTO {

	private Long id;

	@NotNull
	private String externalCode;

	@NotNull
	private String name;

}
