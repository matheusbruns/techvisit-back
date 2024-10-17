package br.com.api.techvisit.authentication;

import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.authentication.definition.LoginResponseDTO;
import br.com.api.techvisit.authentication.definition.RegisterDTO;
import br.com.api.techvisit.authentication.exception.InvalidCredentialsException;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import br.com.api.techvisit.security.TokenService;
import br.com.api.techvisit.user.UserRepository;
import br.com.api.techvisit.user.definition.UserModel;
import br.com.api.techvisit.user.definition.UserResponseDTO;
import jakarta.validation.Valid;

@Service
public class AuthenticationService implements UserDetailsService {

	private final UserRepository userRepository;

	private final TokenService tokenService;

	private final OrganizationService organizationService;

	public AuthenticationService(UserRepository userRepository, TokenService tokenService, OrganizationService organizationService) {
		this.userRepository = userRepository;
		this.tokenService = tokenService;
		this.organizationService = organizationService;
	}

	@Override
	public UserDetails loadUserByUsername(String login)  {
		return this.userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found."));
	}

	public LoginResponseDTO login(@Valid AuthenticationDTO data) {
		UserModel user = this.userRepository.findUserByLogin(data.login())
				.orElseThrow(() -> new InvalidCredentialsException("Usuário ou senha inválidos."));

		if (!user.isActive()) {
			throw new DisabledException("Usuário inativo.");
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(data.password(), user.getPassword())) {
			throw new InvalidCredentialsException("Usuário ou senha inválidos.");
		}

		var token = tokenService.generateToken(user);

		UserResponseDTO userInfos = new UserResponseDTO(user.getId(), user.getLogin(), user.getRole(),
				new OrganizationFactory().buildResponse(user.getOrganization()), user.isActive());
		return new LoginResponseDTO(userInfos, token);
	}

	public ResponseEntity<String> register(@Valid RegisterDTO data) {
		if (this.userRepository.findByLogin(data.login()).isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		OrganizationModel organization = this.organizationService.getOrganizationById(data.organization().getId()).orElseThrow(() -> new OrganizationNotFoundException("Organizatin not found."));
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role(), organization, LocalDate.now(), data.active());

		this.userRepository.save(newUser);

		return ResponseEntity.ok().build();
	}

}
