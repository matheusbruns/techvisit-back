package br.com.api.techvisit.customer.factory;

import java.util.List;

import br.com.api.techvisit.customer.bean.CustomerBean;
import br.com.api.techvisit.customer.model.CustomerModel;

public class CustomerFactory {

	public CustomerBean build(CustomerModel model) {
		CustomerBean bean = new CustomerBean();
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

	public List<CustomerBean> build(List<CustomerModel> organizations) {
		return organizations.stream().map(this::build).toList();
	}

	public CustomerModel build(CustomerBean bean) {
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
