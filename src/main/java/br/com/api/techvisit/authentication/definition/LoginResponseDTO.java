package br.com.api.techvisit.authentication.definition;

import br.com.api.techvisit.user.definition.UserResponseDTO;

public record LoginResponseDTO(UserResponseDTO user, String token) {

}
