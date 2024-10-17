package br.com.api.techvisit.organization;

import java.time.LocalDate;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;

public class OrganizationTestHelper {

	public static OrganizationModel createOrganizationModel() {
		OrganizationModel organizationModel = new OrganizationModel();
		organizationModel.setId(1L);
		organizationModel.setExternalCode("ORG1");
		organizationModel.setName("Organization 1");
		organizationModel.setCreationDate(LocalDate.now());
		organizationModel.setExpirationDate(LocalDate.now().plusDays(30));
		return organizationModel;
	}

	public static OrganizationDTO createOrganizationDTO() {
		OrganizationDTO organizationDTO = new OrganizationDTO();
		organizationDTO.setId(1L);
		organizationDTO.setExternalCode("ORG1");
		organizationDTO.setName("Organization 1");
		organizationDTO.setCreationDate(LocalDate.now());
		organizationDTO.setExpirationDate(LocalDate.now().plusDays(30));
		return organizationDTO;
	}

}
