package br.com.api.techvisit.user.factory;

import java.util.List;

import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import br.com.api.techvisit.user.definition.UserDTO;
import br.com.api.techvisit.user.definition.UserModel;

public class UserFactory {

	public UserDTO build(UserModel user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setLogin(user.getLogin());
		dto.setRole(user.getRole());
		dto.setOrganization(new OrganizationFactory().build(user.getOrganization()));
		dto.setCreationDate(user.getCreationDate());
		dto.setActive(user.isActive());
		return dto;
	}

	public List<UserDTO> build(List<UserModel> users) {
		return users.stream().map(this::build).toList();
	}
	
	public UserModel buildUpdate(UserModel model, UserDTO data, OrganizationModel organization) {
		model.setId(data.getId());
		model.setLogin(data.getLogin());
		model.setRole(data.getRole());
		model.setActive(data.isActive());
		model.setOrganization(organization);
		return model;
	}

}
