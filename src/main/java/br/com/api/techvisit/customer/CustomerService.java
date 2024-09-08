package br.com.api.techvisit.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.factory.CustomerFactory;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrganizationService organizationService;

	public List<CustomerDTO> getAllByOrganization(Long organizationId) {
		return new CustomerFactory().build(this.customerRepository.findAllByOrganizationId(organizationId));
	}

	public CustomerDTO save(CustomerDTO dto) {
		OrganizationModel organization = this.organizationService.getOrganizationById(dto.getOrganization().getId()).orElseThrow(() -> new OrganizationNotFoundException("Organization not found."));
		dto.setOrganization(organization);
		return new CustomerFactory().build(this.customerRepository.save(new CustomerFactory().build(dto)));
	}

	public void delete(List<Long> ids) {
		this.customerRepository.deleteAllByIdInBatch(ids);
	}

}
