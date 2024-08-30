package br.com.api.techvisit.user.definition;

public record RegisterDTO(String login, String password, UserRole role, Long organizationId) {

}
