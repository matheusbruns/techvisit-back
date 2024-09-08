package br.com.api.techvisit.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.techvisit.customer.definition.CustomerModel;

@Repository

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

	List<CustomerModel> findAllByOrganizationId(Long organizationId);

}
