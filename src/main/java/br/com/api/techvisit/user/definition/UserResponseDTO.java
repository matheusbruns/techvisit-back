package br.com.api.techvisit.user.definition;

import br.com.api.techvisit.organization.definition.OrganizationResponseDTO;

public record UserResponseDTO(Long id, String login, UserRole role, OrganizationResponseDTO organization, boolean isActive) {

}
