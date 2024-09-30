package br.com.api.techvisit.technician.definition;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import lombok.Data;

@Data
public class TechnicianSaveDTO {

	private Long id;

	private String name;

	private String login;

	private String cpf;

	private String email;

	private String phoneNumber;

	private String password;
	
	private boolean active;

	private OrganizationDTO organization;

}
