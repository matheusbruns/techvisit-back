package br.com.api.techvisit.user.definition;

import java.time.LocalDate;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	
	private String login;
	
	private UserRole role;
	
	private OrganizationDTO organization;
	
	private LocalDate creationDate;
	
	private boolean active;
	
}
