package br.com.api.techvisit.user.definition;

import br.com.api.techvisit.organization.definition.OrganizationDTO;

public record UserResponseDTO(String login, UserRole role, OrganizationDTO organization) {

}
