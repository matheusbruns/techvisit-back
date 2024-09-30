package br.com.api.techvisit.technician.definition;

import br.com.api.techvisit.generic.GenericModel;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.user.model.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "technicians",  uniqueConstraints = { @UniqueConstraint(columnNames = { "cpf", "organization_id" }) })
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TechnicianModel extends GenericModel {

	@Column(name = "name")
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "cpf", unique = true)
	private String cpf;

	@NotNull
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserModel user;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "organization_id")
	private OrganizationModel organization;

}
