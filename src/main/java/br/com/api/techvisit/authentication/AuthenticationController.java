package br.com.api.techvisit.authentication;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.authentication.definition.LoginResponseDTO;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import br.com.api.techvisit.security.TokenService;
import br.com.api.techvisit.user.UserRepository;
import br.com.api.techvisit.user.definition.AuthenticationDTO;
import br.com.api.techvisit.user.definition.RegisterDTO;
import br.com.api.techvisit.user.definition.UserResponseDTO;
import br.com.api.techvisit.user.model.UserModel;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	TokenService tokenService;

	@Autowired
	OrganizationService organizationService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((UserModel) auth.getPrincipal());
		Optional<UserModel> user = this.userRepository.findUserByLogin(data.login());
		
		UserResponseDTO userInfos = new UserResponseDTO(user.get().getLogin(), user.get().getRole(),  new OrganizationFactory().build(user.get().getOrganization()));
		
		return ResponseEntity.ok(new LoginResponseDTO(userInfos, token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) throws BadRequestException {
		if (this.userRepository.findByLogin(data.login()).isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		OrganizationModel organization = this.organizationService.getOrganizationById(data.organizationId()).orElseThrow(() -> new BadRequestException());
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role(), organization, LocalDate.now(), true);

		this.userRepository.save(newUser);

		return ResponseEntity.ok().build();
	}
}
