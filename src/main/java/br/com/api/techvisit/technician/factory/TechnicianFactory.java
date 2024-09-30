package br.com.api.techvisit.technician.factory;

import java.util.List;

import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.technician.definition.TechnicianSaveDTO;
import br.com.api.techvisit.user.model.UserModel;

public class TechnicianFactory {

	public TechnicianDTO build(TechnicianModel technician) {
		TechnicianDTO dto = new TechnicianDTO();
		dto.setId(technician.getId());
		dto.setLogin(technician.getUser().getLogin());
		dto.setName(technician.getName());
		dto.setCpf(technician.getCpf());
		dto.setEmail(technician.getEmail());
		dto.setPhoneNumber(technician.getPhoneNumber());
		dto.setActive(technician.getUser().isActive());
		return dto;
	}

	public List<TechnicianDTO> build(List<TechnicianModel> technicians) {
		return technicians.stream().map(this::build).toList();
	}

	public TechnicianModel build(TechnicianSaveDTO dto, UserModel user, OrganizationModel organization) {
		TechnicianModel model = new TechnicianModel();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setCpf(dto.getCpf());
		model.setEmail(dto.getEmail());
		model.setPhoneNumber(dto.getPhoneNumber());
		model.setUser(user);
		model.setOrganization(organization);
		return model;
	}

}
