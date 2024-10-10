package br.com.api.techvisit.customer.definition;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerDTO {

	private Long id;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String cpf;

	@NotEmpty
	private String phoneNumber;

	@NotEmpty
	private String state;

	@NotEmpty
	private String city;

	@NotEmpty
	private String neighborhood;

	@NotEmpty
	private String street;

	@NotEmpty
	private String number;

	@NotEmpty
	private String cep;

	private String complement;

	@NotEmpty
	@Valid
	private OrganizationDTO organization;

}
