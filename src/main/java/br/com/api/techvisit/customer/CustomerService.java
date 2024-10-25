package br.com.api.techvisit.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.customer.exception.CannotDeleteCustomerException;
import br.com.api.techvisit.customer.factory.CustomerFactory;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	private final CustomerRepository customerRepository;

	private final OrganizationService organizationService;

	public CustomerService(CustomerRepository customerRepository, OrganizationService organizationService) {
		this.customerRepository = customerRepository;
		this.organizationService = organizationService;
	}

	public List<CustomerDTO> getAllByOrganization(Long organizationId) {
		return new CustomerFactory().build(this.customerRepository.findAllByOrganizationId(organizationId));
	}

	public CustomerDTO save(CustomerDTO dto) {
		OrganizationModel organization = this.organizationService.getOrganizationById(dto.getOrganization().getId())
				.orElseThrow(() -> new OrganizationNotFoundException("Organization not found."));

		dto.setOrganization(new OrganizationFactory().build(organization));
		return new CustomerFactory().build(this.customerRepository.save(new CustomerFactory().build(dto)));
	}

	public void delete(List<Long> ids) {
	    try {
	        this.customerRepository.deleteAllByIdInBatch(ids);
	    } catch (DataIntegrityViolationException e) {
	        throw new CannotDeleteCustomerException("Existem dados vinculados. Não é possível excluir os clientes.");
	    }
	}

	public Optional<CustomerModel> getCustomerById(Long customerId) {
		return this.customerRepository.findById(customerId);
	}

}
