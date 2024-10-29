package br.com.api.techvisit.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.user.definition.UserDTO;
import br.com.api.techvisit.user.definition.UserModel;
import br.com.api.techvisit.user.definition.UserRole;
import br.com.api.techvisit.user.exception.LoginAlreadyExistsException;
import br.com.api.techvisit.user.exception.UserNotFoundException;
import br.com.api.techvisit.user.factory.UserFactory;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final OrganizationService organizationService;
	
	private static final String USER_NOT_FOUNT = "User not found!";
	
	public UserService(UserRepository userRepository, OrganizationService organizationService) {
		this.userRepository = userRepository;
		this.organizationService = organizationService;
	}
	
	public List<UserDTO> getAll() {
		return new UserFactory().build(this.userRepository.findAll());
	}

	@Transactional
	public UserDTO update(@Valid UserDTO data) {
		UserModel user = this.userRepository.findById(data.getId())
				.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUNT));
		
		OrganizationModel organization = this.organizationService.getOrganizationById(data.getOrganization().getId())
				.orElseThrow(() -> new OrganizationNotFoundException("Organizatin not found."));
		
		this.userRepository.save(new UserFactory().buildUpdate(user, data, organization));
		return data;
	}

	public void delete(List<Long> ids) {
		for (Long id : ids) {
			try {
				this.userRepository.deleteById(id);
			} catch (Exception e) {
				Optional<UserModel> user = this.userRepository.findById(id);
				if (user.isPresent()) {
					user.get().setActive(false);
					this.userRepository.save(user.get());
				}
			}
		}

	}

	public UserModel saveTechnician(String login, String password, boolean active, OrganizationModel organization) {

		Optional<UserModel> user = this.userRepository.findUserByLogin(login);

		if (user.isPresent()) {
			throw new LoginAlreadyExistsException("The login '" + login + "' already exists.");
		}

		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		UserModel newUser = new UserModel(login, encryptedPassword, UserRole.TECHNICIAN, organization, LocalDate.now(), active);

		return this.userRepository.save(newUser);
	}

	public void updatePasswordAndActive(String login, String password, boolean active) {
		UserModel user = this.userRepository.findUserByLogin(login)
				.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUNT));
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		user.setActive(active);
		this.userRepository.save(user);
	}

	public UserDTO updatePassword(AuthenticationDTO loginInfo) {
		UserModel user = this.userRepository.findUserByLogin(loginInfo.login())
				.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUNT));

		String encryptedPassword = new BCryptPasswordEncoder().encode(loginInfo.password());
		user.setPassword(encryptedPassword);
		return new UserFactory().build(this.userRepository.save(user));
	}

}
