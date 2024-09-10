package br.com.api.techvisit.authentication;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.authentication.definition.LoginResponseDTO;
import br.com.api.techvisit.authentication.definition.RegisterDTO;
import br.com.api.techvisit.organization.OrganizationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	OrganizationService organizationService;

	@Autowired
	AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		return ResponseEntity.ok(this.authenticationService.login(data));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) throws BadRequestException {
		return this.authenticationService.register(data);
	}
}
