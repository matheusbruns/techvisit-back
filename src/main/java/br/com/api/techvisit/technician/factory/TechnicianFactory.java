package br.com.api.techvisit.technician.factory;

import java.util.List;

import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianModel;

public class TechnicianFactory {

	private TechnicianDTO build(TechnicianModel technician) {
		TechnicianDTO dto = new TechnicianDTO();
		dto.setId(technician.getId());
		dto.setLogin(technician.getUser().getLogin());
		dto.setName(technician.getName());
		dto.setCpf(technician.getCpf());
		dto.setEmail(technician.getEmail());
		dto.setPhoneNumber(technician.getPhoneNumber());
		return dto;
	}

	public List<TechnicianDTO> build(List<TechnicianModel> technicians) {
		return technicians.stream().map(this::build).toList();
	}

}
