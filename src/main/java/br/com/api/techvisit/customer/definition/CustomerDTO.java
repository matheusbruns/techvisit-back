package br.com.api.techvisit.customer.definition;

import br.com.api.techvisit.organization.definition.OrganizationModel;
import lombok.Data;

@Data
public class CustomerDTO {

	private Long id;

	private String firstName;

	private String lastName;

	private String cpf;

	private String phoneNumber;

	private String street;

	private String number;

	private String complement;

	private String cep;

	private OrganizationModel organization;

}
