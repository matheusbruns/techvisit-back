package br.com.api.techvisit.technician;

import java.time.LocalDate;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.technician.definition.TechnicianSaveDTO;
import br.com.api.techvisit.user.definition.UserModel;

public class TechnicianTestHelper {

	public static TechnicianDTO createTechnicianDTO() {
		TechnicianDTO technicianDTO = new TechnicianDTO();
		technicianDTO.setId(1L);
		technicianDTO.setName("Technician Name");
		technicianDTO.setLogin("technician1");
		technicianDTO.setCpf("12345678900");
		technicianDTO.setEmail("tech@example.com");
		technicianDTO.setPhoneNumber("123456789");
		technicianDTO.setActive(true);
		return technicianDTO;
	}

	public static TechnicianModel createTechnicianModel() {
		TechnicianModel technicianModel = new TechnicianModel();
		technicianModel.setId(1L);
		technicianModel.setName("Technician Name");
		technicianModel.setEmail("tech@example.com");
		technicianModel.setPhoneNumber("123456789");
		technicianModel.setCpf("12345678900");
		technicianModel.setUser(createUserModel());
		technicianModel.setOrganization(createOrganizationModel());
		return technicianModel;
	}

	public static TechnicianSaveDTO createTechnicianSaveDTO() {
		TechnicianSaveDTO technicianSaveDTO = new TechnicianSaveDTO();
		technicianSaveDTO.setId(1L);
		technicianSaveDTO.setName("Technician Name");
		technicianSaveDTO.setLogin("technician1");
		technicianSaveDTO.setPassword("password");
		technicianSaveDTO.setCpf("12345678900");
		technicianSaveDTO.setEmail("tech@example.com");
		technicianSaveDTO.setPhoneNumber("123456789");
		technicianSaveDTO.setActive(true);
		technicianSaveDTO.setOrganization(createOrganizationDTO());
		return technicianSaveDTO;
	}

	public static UserModel createUserModel() {
		UserModel userModel = new UserModel();
		userModel.setId(1L);
		userModel.setLogin("technician1");
		userModel.setActive(true);
		return userModel;
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
