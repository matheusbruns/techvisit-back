package br.com.api.techvisit.user.definition;

import br.com.api.techvisit.organization.bean.OrganizationBean;

public record UserResponseDTO(String login, UserRole role, OrganizationBean organization) {

}
