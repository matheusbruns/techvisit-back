package br.com.api.techvisit.authentication;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.authentication.definition.LoginResponseDTO;
import br.com.api.techvisit.authentication.definition.RegisterDTO;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import br.com.api.techvisit.security.TokenService;
import br.com.api.techvisit.user.UserRepository;
import br.com.api.techvisit.user.definition.UserResponseDTO;
import br.com.api.techvisit.user.model.UserModel;
import jakarta.validation.Valid;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return this.userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found."));
	}

	public LoginResponseDTO login(@Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((UserModel) auth.getPrincipal());
		Optional<UserModel> user = this.userRepository.findUserByLogin(data.login());
		
		UserResponseDTO userInfos = new UserResponseDTO(user.get().getLogin(), user.get().getRole(),  new OrganizationFactory().buildResponse(user.get().getOrganization()));
		return new LoginResponseDTO(userInfos, token);
	}

	public ResponseEntity<?> register(@Valid RegisterDTO data) throws BadRequestException {
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
