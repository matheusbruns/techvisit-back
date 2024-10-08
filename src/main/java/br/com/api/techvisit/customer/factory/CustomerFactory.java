package br.com.api.techvisit.customer.factory;

import java.util.List;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.organization.factory.OrganizationFactory;

public class CustomerFactory {

	public CustomerDTO build(CustomerModel model) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(model.getId());
		dto.setFirstName(model.getFirstName());
		dto.setLastName(model.getLastName());
		dto.setCpf(model.getCpf());
		dto.setPhoneNumber(model.getPhoneNumber());
		dto.setState(model.getState());
		dto.setCity(model.getCity());
		dto.setNeighborhood(model.getNeighborhood());
		dto.setStreet(model.getStreet());
		dto.setNumber(model.getNumber());
		dto.setComplement(model.getComplement());
		dto.setCep(model.getCep());
		dto.setOrganization(new OrganizationFactory().build(model.getOrganization()));
		return dto;
	}

	public List<CustomerDTO> build(List<CustomerModel> customers) {
		return customers.stream().map(this::build).toList();
	}

	public CustomerModel build(CustomerDTO dto) {
		CustomerModel model = new CustomerModel();
		model.setId(dto.getId());
		model.setFirstName(dto.getFirstName());
		model.setLastName(dto.getLastName());
		model.setCpf(dto.getCpf());
		model.setPhoneNumber(dto.getPhoneNumber());
		model.setState(dto.getState());
		model.setCity(dto.getCity());
		model.setNeighborhood(dto.getNeighborhood());
		model.setStreet(dto.getStreet());
		model.setNumber(dto.getNumber());
		model.setComplement(dto.getComplement());
		model.setCep(dto.getCep());
		model.setOrganization(new OrganizationFactory().build(dto.getOrganization()));
		return model;
	}

}
