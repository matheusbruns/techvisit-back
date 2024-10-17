package br.com.api.techvisit.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.user.definition.UserDTO;
import br.com.api.techvisit.user.definition.UserModel;
import br.com.api.techvisit.user.definition.UserRole;
import br.com.api.techvisit.user.exception.LoginAlreadyExistsException;
import br.com.api.techvisit.user.exception.UserNotFoundException;

class UserTest {

	private UserRepository userRepository;
	private OrganizationService organizationService;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		userRepository = mock(UserRepository.class);
		organizationService = mock(OrganizationService.class);
		userService = new UserService(userRepository, organizationService);
	}

	@Test
	void testUpdate_UserNotFound() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.update(userDTO));
	}

	@Test
	void testUpdate_OrganizationNotFound() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setOrganization(new OrganizationDTO());

		UserModel existingUser = new UserModel();
		existingUser.setId(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
		when(organizationService.getOrganizationById(anyLong())).thenReturn(Optional.empty());

		assertThrows(OrganizationNotFoundException.class, () -> userService.update(userDTO));
	}

	@Test
	void testDelete() {
		List<Long> ids = Arrays.asList(1L, 2L);

		doNothing().when(userRepository).deleteById(anyLong());

		userService.delete(ids);

		verify(userRepository, times(2)).deleteById(anyLong());
	}

	@Test
	void testSaveTechnician_Success() {
		String login = "techUser";
		String password = "password";
		boolean active = true;
		OrganizationModel organization = new OrganizationModel();

		when(userRepository.findUserByLogin(login)).thenReturn(Optional.empty());
		when(userRepository.save(any(UserModel.class))).thenAnswer(i -> i.getArguments()[0]);

		UserModel result = userService.saveTechnician(login, password, active, organization);

		assertEquals(login, result.getLogin());
		assertTrue(new BCryptPasswordEncoder().matches(password, result.getPassword()));
		assertEquals(UserRole.TECHNICIAN, result.getRole());
		assertEquals(organization, result.getOrganization());
		assertEquals(active, result.isActive());
	}

	@Test
	void testSaveTechnician_LoginAlreadyExists() {
		String login = "existingUser";
		String password = "password";
		boolean active = true;
		OrganizationModel organization = new OrganizationModel();

		when(userRepository.findUserByLogin(login)).thenReturn(Optional.of(new UserModel()));

		assertThrows(LoginAlreadyExistsException.class,
				() -> userService.saveTechnician(login, password, active, organization));
	}

	@Test
	void testUpdatePasswordAndActive_Success() {
		String login = "userLogin";
		String newPassword = "newPassword";
		boolean active = false;

		UserModel existingUser = new UserModel();
		existingUser.setLogin(login);

		when(userRepository.findUserByLogin(login)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(UserModel.class))).thenAnswer(i -> i.getArguments()[0]);

		userService.updatePasswordAndActive(login, newPassword, active);

		assertTrue(new BCryptPasswordEncoder().matches(newPassword, existingUser.getPassword()));
		assertEquals(active, existingUser.isActive());
	}

	@Test
	void testUpdatePasswordAndActive_UserNotFound() {
		String login = "nonExistingUser";
		String newPassword = "newPassword";
		boolean active = false;

		when(userRepository.findUserByLogin(login)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class,
				() -> userService.updatePasswordAndActive(login, newPassword, active));
	}

}
