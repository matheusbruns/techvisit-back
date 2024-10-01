package br.com.api.techvisit.visitschedule.definition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.generic.GenericModel;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit_schedules", indexes = { @Index(name = "idx_organization_id", columnList = "organization_id") })
@EqualsAndHashCode(callSuper = true)
public class VisitScheduleModel extends GenericModel {

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerModel customer;

	@ManyToOne
	@JoinColumn(name = "technician_id")
	private TechnicianModel technician;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "neighborhood")
	private String neighborhood;

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

	@Enumerated(EnumType.STRING)
	private VisitStatus status;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "comment", length = 1000)
	private String comment;

}