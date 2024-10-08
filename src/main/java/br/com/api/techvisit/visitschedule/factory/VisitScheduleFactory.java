package br.com.api.techvisit.visitschedule.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.customer.factory.CustomerFactory;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.technician.factory.TechnicianFactory;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleDTO;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleModel;

public class VisitScheduleFactory {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

	public VisitScheduleDTO build(VisitScheduleModel model) {
		VisitScheduleDTO dto = new VisitScheduleDTO();
		dto.setId(model.getId());
		dto.setDescription(model.getDescription());
		dto.setCustomer(new CustomerFactory().build(model.getCustomer()));
		dto.setTechnician(new TechnicianFactory().build(model.getTechnician()));
		dto.setDescription(model.getDescription());
		dto.setDescription(model.getDescription());
		dto.setState(model.getState());
		dto.setCity(model.getCity());
		dto.setNeighborhood(model.getNeighborhood());
		dto.setStreet(model.getStreet());
		dto.setNumber(model.getNumber());
		dto.setComplement(model.getComplement());
		dto.setCep(model.getCep());
		dto.setPrice(model.getPrice());
		dto.setComment(model.getComment());
		dto.setStartDate(model.getStartDate() != null ? model.getStartDate().format(FORMATTER) : null);
		dto.setEndDate(model.getEndDate() != null ? model.getEndDate().format(FORMATTER) : null);
		return dto;
	}

	public List<VisitScheduleDTO> build(List<VisitScheduleModel> visitSchedules) {
		return visitSchedules.stream().map(this::build).toList();
	}

	public VisitScheduleModel build(VisitScheduleDTO dto, OrganizationModel organization, CustomerModel customer,
			TechnicianModel technician) {
		VisitScheduleModel model = new VisitScheduleModel();
		model.setId(dto.getId());
		model.setDescription(dto.getDescription());
		model.setCustomer(customer);
		model.setTechnician(technician);
		model.setOrganization(organization);
		model.setDescription(dto.getDescription());
		model.setDescription(dto.getDescription());
		model.setState(dto.getState());
		model.setCity(dto.getCity());
		model.setNeighborhood(dto.getNeighborhood());
		model.setStreet(dto.getStreet());
		model.setNumber(dto.getNumber());
		model.setComplement(dto.getComplement());
		model.setCep(dto.getCep());
		model.setPrice(dto.getPrice());
		model.setComment(dto.getComment());
		model.setStartDate(this.millisToLocalDateTime(dto.getStartDate()));
		model.setEndDate(this.millisToLocalDateTime(dto.getEndDate()));
		return model;
	}

	private LocalDateTime millisToLocalDateTime(String timeInMillis) {
		Long timeInMillisLongValue = Long.valueOf(timeInMillis);
		return timeInMillis != null
				? LocalDateTime.ofInstant(Instant.ofEpochMilli(timeInMillisLongValue), ZoneId.of("America/Sao_Paulo"))
				: null;
	}

}