package br.com.api.techvisit.organization.definition;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.api.techvisit.generic.GenericModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "organizations")
@EqualsAndHashCode(callSuper = true)
public class OrganizationModel extends GenericModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "external_code", unique = true)
	private String externalCode;

	@Column(name = "name")
	private String name;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

}
