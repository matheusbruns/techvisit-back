package br.com.api.techvisit.organization.factory;

import java.util.List;

import br.com.api.techvisit.organization.bean.OrganizationBean;
import br.com.api.techvisit.organization.model.OrganizationModel;

public class OrganizationFactory {

	public OrganizationBean build(OrganizationModel model) {
		OrganizationBean bean = new OrganizationBean();
		bean.setId(model.getId());
		bean.setExternalCode(model.getExternalCode());
		bean.setName(model.getName());
		bean.setCreationDate(model.getCreationDate());
		bean.setExpirationDate(model.getExpirationDate());
		return bean;
	}

	public List<OrganizationBean> build(List<OrganizationModel> organizations) {
		return organizations.stream().map(this::build).toList();
	}

	public OrganizationModel build(OrganizationBean bean) {
		OrganizationModel model = new OrganizationModel();
		model.setId(bean.getId());
		model.setExternalCode(bean.getExternalCode());
		model.setName(bean.getName());
		model.setCreationDate(bean.getCreationDate());
		model.setExpirationDate(bean.getExpirationDate());
		return model;
	}

}
