package br.com.api.techvisit.authentication.definition;

import br.com.api.techvisit.user.definition.UserRole;

public record RegisterDTO(String login, String password, UserRole role, Long organizationId) {

}
