package br.com.api.techvisit.customer.bean;

import br.com.api.techvisit.organization.model.OrganizationModel;
import lombok.Data;

@Data
public class CustomerBean {

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
