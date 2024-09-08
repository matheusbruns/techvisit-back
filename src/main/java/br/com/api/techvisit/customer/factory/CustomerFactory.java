package br.com.api.techvisit.customer.factory;

import java.util.List;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.definition.CustomerModel;

public class CustomerFactory {

	public CustomerDTO build(CustomerModel model) {
		CustomerDTO bean = new CustomerDTO();
		bean.setId(model.getId());
		bean.setFirstName(model.getFirstName());
		bean.setLastName(model.getLastName());
		bean.setCpf(model.getCpf());
		bean.setPhoneNumber(model.getPhoneNumber());
		bean.setStreet(model.getStreet());
		bean.setNumber(model.getNumber());
		bean.setComplement(model.getComplement());
		bean.setCep(model.getCep());
		bean.setOrganization(model.getOrganization());
		return bean;
	}

	public List<CustomerDTO> build(List<CustomerModel> organizations) {
		return organizations.stream().map(this::build).toList();
	}

	public CustomerModel build(CustomerDTO bean) {
		CustomerModel model = new CustomerModel();
		model.setId(bean.getId());
		model.setFirstName(bean.getFirstName());
		model.setLastName(bean.getLastName());
		model.setCpf(bean.getCpf());
		model.setPhoneNumber(bean.getPhoneNumber());
		model.setStreet(bean.getStreet());
		model.setNumber(bean.getNumber());
		model.setComplement(bean.getComplement());
		model.setCep(bean.getCep());
		model.setOrganization(bean.getOrganization());
		return model;
	}

}
