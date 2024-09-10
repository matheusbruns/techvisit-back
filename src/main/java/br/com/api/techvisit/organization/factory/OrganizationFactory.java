package br.com.api.techvisit.organization.factory;

import java.time.LocalDate;
import java.util.List;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.definition.OrganizationResponseDTO;

public class OrganizationFactory {

	public OrganizationDTO build(OrganizationModel model) {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(model.getId());
		dto.setExternalCode(model.getExternalCode());
		dto.setName(model.getName());
		dto.setCreationDate(model.getCreationDate());
		dto.setExpirationDate(model.getExpirationDate());
		return dto;
	}

	public List<OrganizationDTO> build(List<OrganizationModel> organizations) {
		return organizations.stream().map(this::build).toList();
	}

	public OrganizationModel build(OrganizationDTO dto) {
		OrganizationModel model = new OrganizationModel();
		model.setId(dto.getId());
		model.setExternalCode(dto.getExternalCode());
		model.setName(dto.getName());
		model.setCreationDate(dto.getCreationDate());
		model.setExpirationDate(dto.getExpirationDate());
		return model;
	}

	public OrganizationModel buildNew(OrganizationDTO dto) {
		OrganizationModel model = new OrganizationModel();
		model.setId(dto.getId());
		model.setExternalCode(dto.getExternalCode());
		model.setName(dto.getName());
		model.setCreationDate(LocalDate.now());
		model.setExpirationDate(dto.getExpirationDate());
		return model;
	}

	public OrganizationDTO build(Long id, String externalCode, String name, LocalDate creationDate, LocalDate expirationDate) {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(id);
		dto.setExternalCode(externalCode);
		dto.setName(name);
		dto.setCreationDate(creationDate);
		dto.setExpirationDate(expirationDate);
		return dto;
	}

	public OrganizationResponseDTO buildResponse(OrganizationModel model) {
		OrganizationResponseDTO dto = new OrganizationResponseDTO();
		dto.setId(model.getId());
		dto.setExternalCode(model.getExternalCode());
		dto.setName(model.getName());
		return dto;
	}

}
