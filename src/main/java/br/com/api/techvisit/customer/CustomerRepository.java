package br.com.api.techvisit.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.techvisit.customer.model.CustomerModel;

@Repository

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

	CustomerModel findAllByOrganizationId(Long organizationId);

}
