package br.com.api.techvisit.visitschedule;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.api.techvisit.customer.CustomerService;
import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.customer.exception.CustomerNotFoundException;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.technician.TechnicianService;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.technician.exception.TechnicianNotFoundException;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleDTO;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleModel;
import br.com.api.techvisit.visitschedule.definition.VisitStatus;
import br.com.api.techvisit.visitschedule.exception.VisitScheduleNotFoundException;
import br.com.api.techvisit.visitschedule.factory.VisitScheduleFactory;

@Service
public class VisitScheduleService {

	private final VisitScheduleRepository visitScheduleRepository;
	
	private final CustomerService customerService;

	private final TechnicianService technicianService;

	private final OrganizationService organizationService;

	public VisitScheduleService(VisitScheduleRepository visitScheduleRepository, CustomerService customerService,
			TechnicianService technicianService, OrganizationService organizationService) {

		this.visitScheduleRepository = visitScheduleRepository;
		this.customerService = customerService;
		this.technicianService = technicianService;
		this.organizationService = organizationService;
	}

	public List<VisitScheduleDTO> getAll(Long organizationId) {
		return new VisitScheduleFactory().build(this.visitScheduleRepository.findAllByOrganizationId(organizationId));
	}

	public void save(VisitScheduleDTO dto) {
		VisitScheduleFactory factory = new VisitScheduleFactory();

		OrganizationModel organization = this.organizationService.getOrganizationById(dto.getOrganization().getId())
				.orElseThrow(() -> new OrganizationNotFoundException("Organization not found."));

		CustomerModel customer = this.customerService.getCustomerById(dto.getCustomer().getId())
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found."));

		TechnicianModel technician = this.technicianService.getTechnicianById(dto.getTechnician().getId())
				.orElseThrow(() -> new TechnicianNotFoundException("Technician not found."));

		this.visitScheduleRepository.save(factory.build(dto, organization, customer, technician));
	}

	public void delete(List<Long> ids) {
		this.visitScheduleRepository.deleteAllByIdInBatch(ids);
	}

	public VisitScheduleDTO editStatus(Long visitScheduleId, VisitStatus status) {
		VisitScheduleModel visitScheduleModel = this.visitScheduleRepository.findById(visitScheduleId)
				.orElseThrow(() -> new VisitScheduleNotFoundException("Visit schedule not found."));

		visitScheduleModel.setStatus(status);
		return new VisitScheduleFactory().build(this.visitScheduleRepository.save(visitScheduleModel));
	}

	public List<VisitScheduleDTO> getAllByUserId(Long organizationId, Long userId) {
		return new VisitScheduleFactory().build(this.visitScheduleRepository.findAllByOrganizationIdAndUserId(organizationId, userId));
	}

	public VisitScheduleDTO updateVisit(VisitScheduleDTO visitScheduleDTO) {
		VisitScheduleModel visitSchedule = this.visitScheduleRepository.findById(visitScheduleDTO.getId())
				.orElseThrow(() -> new VisitScheduleNotFoundException("Visit schedule not found."));
		
		visitSchedule.setPrice(visitScheduleDTO.getPrice());
		visitSchedule.setComment(visitScheduleDTO.getComment());
		visitSchedule.setStatus(VisitStatus.ATTENDED);
		return new VisitScheduleFactory().build(this.visitScheduleRepository.save(visitSchedule));
	}

}
