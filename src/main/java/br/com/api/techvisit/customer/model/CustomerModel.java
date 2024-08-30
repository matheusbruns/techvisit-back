package br.com.api.techvisit.customer.model;

import br.com.api.techvisit.generic.GenericModel;
import br.com.api.techvisit.organization.model.OrganizationModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "customer")
@EqualsAndHashCode(callSuper = true)
public class CustomerModel extends GenericModel {

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "street")
	private String street;

	@Column(name = "number")
	private String number;

	@Column(name = "complement")
	private String complement;

	@Column(name = "cep")
	private String cep;

	@ManyToOne
	@JoinColumn(name = "organization_id")
	private OrganizationModel organization;

}
