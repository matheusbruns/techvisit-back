package br.com.api.techvisit.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public String getAllByOrganization(Long organizationId) {
		this.customerRepository.findAllByOrganizationId(organizationId);
		return null;
	}

}
