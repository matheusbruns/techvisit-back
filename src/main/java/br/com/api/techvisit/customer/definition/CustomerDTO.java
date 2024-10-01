package br.com.api.techvisit.customer.definition;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import lombok.Data;

@Data
public class CustomerDTO {

	private Long id;

	private String firstName;

	private String lastName;

	private String cpf;

	private String phoneNumber;

	private String state;

	private String city;
	
	private String neighborhood;

	private String street;

	private String number;

	private String complement;

	private String cep;

	private OrganizationDTO organization;

}
