package br.com.api.techvisit.visitschedule.definition;

import java.math.BigDecimal;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VisitScheduleDTO {

	private Long id;

	@NotEmpty
	private String description;

	@NotEmpty
	private CustomerDTO customer;

	@NotEmpty
	@Valid
	private TechnicianDTO technician;

	@NotEmpty
	private String city;

	@NotEmpty
	private String state;

	@NotEmpty
	private String neighborhood;

	@NotEmpty
	private String street;

	@NotEmpty
	private String number;

	@NotEmpty
	private String cep;

	private BigDecimal price;

	@NotEmpty
	private String startDate;

	@NotEmpty
	private String endDate;

	private String comment;

	private String complement;

	@NotEmpty
	@Valid
	private OrganizationDTO organization;

	private VisitStatus status;

}
