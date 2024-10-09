package br.com.api.techvisit.visitschedule.definition;

import java.math.BigDecimal;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import lombok.Data;

@Data
public class VisitScheduleDTO {

	private Long id;

	private String description;

	private CustomerDTO customer;

	private TechnicianDTO technician;

	private String phoneNumber;

	private String city;

	private String state;

	private String neighborhood;

	private String street;

	private String number;

	private String complement;

	private String cep;

	private BigDecimal price;

	private String startDate;

	private String endDate;

	private String comment;

	private OrganizationDTO organization;

	private VisitStatus status;
	
}
