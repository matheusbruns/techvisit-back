package br.com.api.techvisit.visitschedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.api.techvisit.visitschedule.factory.VisitScheduleFactory;

@Service
public class VisitScheduleService {

	@Autowired
	private VisitScheduleRepository visitScheduleRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TechnicianService technicianService;

	@Autowired
	private OrganizationService organizationService;

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

}
