package br.com.api.techvisit.customer;

import java.time.LocalDate;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;

public class CustomerTestHelper {

	public static CustomerDTO createCustomerDTO() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setFirstName("John");
		customerDTO.setLastName("Doe");
		customerDTO.setCpf("12345678900");
		customerDTO.setPhoneNumber("555-1234");
		customerDTO.setState("State");
		customerDTO.setCity("City");
		customerDTO.setNeighborhood("Neighborhood");
		customerDTO.setStreet("Street");
		customerDTO.setNumber("123");
		customerDTO.setCep("12345-678");
		customerDTO.setOrganization(createOrganizationDTO());
		return customerDTO;
	}

	public static CustomerModel createCustomerModel() {
		CustomerModel customerModel = new CustomerModel();
		customerModel.setId(1L);
		customerModel.setFirstName("John");
		customerModel.setLastName("Doe");
		customerModel.setCpf("12345678900");
		customerModel.setPhoneNumber("555-1234");
		customerModel.setState("State");
		customerModel.setCity("City");
		customerModel.setNeighborhood("Neighborhood");
		customerModel.setStreet("Street");
		customerModel.setNumber("123");
		customerModel.setCep("12345-678");
		customerModel.setOrganization(createOrganizationModel());
		return customerModel;
	}

	public static OrganizationDTO createOrganizationDTO() {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(1L);
		dto.setExternalCode("EXT123");
		dto.setName("TechVisit Organization");
		dto.setCreationDate(LocalDate.now());
		dto.setExpirationDate(LocalDate.now().plusYears(1));
		return dto;
	}

	public static OrganizationModel createOrganizationModel() {
		OrganizationModel model = new OrganizationModel();
		model.setId(1L);
		model.setExternalCode("TECHVISIT");
		model.setName("TechVisit");
		model.setCreationDate(LocalDate.now());
		model.setExpirationDate(LocalDate.now().plusYears(1));
		return model;
	}

}
