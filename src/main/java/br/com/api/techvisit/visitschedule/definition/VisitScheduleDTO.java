package br.com.api.techvisit.visitschedule.definition;

import java.math.BigDecimal;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import lombok.Data;

@Data
public class VisitScheduleDTO {

	public Long id;

	public String description;

	public CustomerDTO customer;

	public TechnicianDTO technician;

	public String phoneNumber;

	public String city;

	public String state;

	public String neighborhood;

	public String street;

	public String number;

	public String complement;

	public String cep;

	public BigDecimal price;

	public String startDate;

	public String endDate;

	public String comment;

	public OrganizationDTO organization;

}
