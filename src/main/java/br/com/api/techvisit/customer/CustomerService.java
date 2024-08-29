package br.com.api.techvisit.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.customer.bean.CustomerBean;
import br.com.api.techvisit.customer.factory.CustomerFactory;
import br.com.api.techvisit.customer.model.CustomerModel;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.model.OrganizationModel;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrganizationService organizationService;

	public List<CustomerBean> getAllByOrganization(Long organizationId) {
		return new CustomerFactory().build(this.customerRepository.findAllByOrganizationId(organizationId));
	}

	public CustomerBean save(CustomerBean customerBean) {
		
		Optional<OrganizationModel> organization = this.organizationService.getOrganization(customerBean.getOrganization().getId());
		
		CustomerModel model = new CustomerFactory().build(customerBean);
		
		
		return null;
	}

}
