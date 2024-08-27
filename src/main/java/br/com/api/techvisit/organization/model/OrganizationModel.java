package br.com.api.techvisit.organization.model;

import java.time.LocalDate;

import br.com.api.techvisit.generic.GenericModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "organization")
@EqualsAndHashCode(callSuper = true)
public class OrganizationModel extends GenericModel {

	@Column(name = "external_code", unique = true)
	private String externalCode;

	@Column(name = "name")
	private String name;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

}
