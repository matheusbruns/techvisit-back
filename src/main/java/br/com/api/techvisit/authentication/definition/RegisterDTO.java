package br.com.api.techvisit.authentication.definition;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.user.definition.UserRole;

public record RegisterDTO(String login, String password, UserRole role, OrganizationDTO organization, Boolean active) {

}
