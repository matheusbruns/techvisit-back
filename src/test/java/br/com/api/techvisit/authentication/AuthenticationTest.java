package br.com.api.techvisit.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.authentication.definition.LoginResponseDTO;
import br.com.api.techvisit.authentication.definition.RegisterDTO;
import br.com.api.techvisit.authentication.exception.InvalidCredentialsException;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.security.TokenService;
import br.com.api.techvisit.user.UserRepository;
import br.com.api.techvisit.user.definition.UserModel;
import br.com.api.techvisit.user.definition.UserResponseDTO;
import br.com.api.techvisit.user.definition.UserRole;

class AuthenticationTest {

	@InjectMocks
	private AuthenticationController authenticationController;

	@Mock
	private AuthenticationService authenticationService;

	@InjectMocks
	private AuthenticationService authenticationServiceUnderTest;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TokenService tokenService;

	@Mock
	private OrganizationService organizationService;

	private AuthenticationDTO authenticationDTO;
	private RegisterDTO registerDTO;
	private UserModel userModel;
	private OrganizationModel organizationModel;
	private OrganizationDTO organizationDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		organizationDTO = new OrganizationDTO();
		organizationDTO.setId(1L);

		organizationModel = new OrganizationModel();
		organizationModel.setId(1L);

		userModel = new UserModel();
		userModel.setId(1L);
		userModel.setLogin("user1");
		userModel.setPassword(new BCryptPasswordEncoder().encode("password"));
		userModel.setRole(UserRole.USER);
		userModel.setOrganization(organizationModel);
		userModel.setCreationDate(LocalDate.now());
		userModel.setActive(true);

		authenticationDTO = new AuthenticationDTO("user1", "password");

		registerDTO = new RegisterDTO("user2", "password", UserRole.USER, organizationDTO, true);
	}

	@Test
	void testLogin_Success() {
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO(new UserResponseDTO(userModel.getId(),
				userModel.getLogin(), userModel.getRole(), null, userModel.isActive()), "token");
		when(authenticationService.login(any(AuthenticationDTO.class))).thenReturn(loginResponseDTO);

		ResponseEntity<LoginResponseDTO> response = authenticationController.login(authenticationDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("token", response.getBody().token());
		verify(authenticationService, times(1)).login(any(AuthenticationDTO.class));
	}

	@Test
	void testRegister_Success() {
		when(authenticationService.register(any(RegisterDTO.class))).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<String> response = authenticationController.register(registerDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(authenticationService, times(1)).register(any(RegisterDTO.class));
	}

	@Test
	void testLoadUserByUsername_Success() {
		when(userRepository.findByLogin("user1")).thenReturn(Optional.of(userModel));

		UserModel result = (UserModel) authenticationServiceUnderTest.loadUserByUsername("user1");

		assertNotNull(result);
		assertEquals("user1", result.getUsername());
		verify(userRepository, times(1)).findByLogin("user1");
	}

	@Test
	void testLoadUserByUsername_UserNotFound() {
		when(userRepository.findByLogin("user1")).thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> {
			authenticationServiceUnderTest.loadUserByUsername("user1");
		});

		verify(userRepository, times(1)).findByLogin("user1");
	}

	@Test
	void testLogin_Success_Service() {
		when(userRepository.findUserByLogin("user1")).thenReturn(Optional.of(userModel));
		when(tokenService.generateToken(userModel)).thenReturn("token");

		LoginResponseDTO result = authenticationServiceUnderTest.login(authenticationDTO);

		assertNotNull(result);
		assertEquals("token", result.token());
		assertEquals("user1", result.user().login());

		verify(userRepository, times(1)).findUserByLogin("user1");
		verify(tokenService, times(1)).generateToken(userModel);
	}

	@Test
	void testLogin_UserNotFound_Service() {
		when(userRepository.findUserByLogin("user1")).thenReturn(Optional.empty());

		assertThrows(InvalidCredentialsException.class, () -> {
			authenticationServiceUnderTest.login(authenticationDTO);
		});

		verify(userRepository, times(1)).findUserByLogin("user1");
		verifyNoInteractions(tokenService);
	}

	@Test
	void testRegister_Success_Service() {
		when(userRepository.findByLogin("user2")).thenReturn(Optional.empty());
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

		ResponseEntity<String> response = authenticationServiceUnderTest.register(registerDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(userRepository, times(1)).findByLogin("user2");
		verify(organizationService, times(1)).getOrganizationById(1L);
		verify(userRepository, times(1)).save(any(UserModel.class));
	}

	@Test
	void testRegister_UserAlreadyExists() {
		when(userRepository.findByLogin("user2")).thenReturn(Optional.of(userModel));

		ResponseEntity<String> response = authenticationServiceUnderTest.register(registerDTO);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		verify(userRepository, times(1)).findByLogin("user2");
		verifyNoInteractions(organizationService);
		verify(userRepository, times(0)).save(any(UserModel.class));
	}

	@Test
	void testRegister_OrganizationNotFound() {
		when(userRepository.findByLogin("user2")).thenReturn(Optional.empty());
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.empty());

		assertThrows(OrganizationNotFoundException.class, () -> {
			authenticationServiceUnderTest.register(registerDTO);
		});

		verify(userRepository, times(1)).findByLogin("user2");
		verify(organizationService, times(1)).getOrganizationById(1L);
		verify(userRepository, times(0)).save(any(UserModel.class));
	}

	@Test
	void testGenerateToken_Success() {
		TokenService tokenServiceUnderTest = new TokenService();
		tokenServiceUnderTest.secret = "testsecret";

		String token = tokenServiceUnderTest.generateToken(userModel);

		assertNotNull(token);
	}

	@Test
	void testValidateToken_Success() {
		TokenService tokenServiceUnderTest = new TokenService();
		tokenServiceUnderTest.secret = "testsecret";

		String token = tokenServiceUnderTest.generateToken(userModel);
		String username = tokenServiceUnderTest.validateToken(token);

		assertEquals("user1", username);
	}

	@Test
	void testValidateToken_InvalidToken() {
		TokenService tokenServiceUnderTest = new TokenService();
		tokenServiceUnderTest.secret = "testsecret";

		String username = tokenServiceUnderTest.validateToken("invalidtoken");

		assertEquals("", username);
	}

}
