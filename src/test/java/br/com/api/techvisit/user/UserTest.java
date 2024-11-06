package br.com.api.techvisit.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.user.definition.UserDTO;
import br.com.api.techvisit.user.definition.UserModel;
import br.com.api.techvisit.user.definition.UserRole;
import br.com.api.techvisit.user.exception.LoginAlreadyExistsException;
import br.com.api.techvisit.user.exception.UserNotFoundException;
import br.com.api.techvisit.user.factory.UserFactory;

class UserTest {

	private UserRepository userRepository;
	private OrganizationService organizationService;
	private UserFactory userFactory;

	@InjectMocks
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		userRepository = mock(UserRepository.class);
		organizationService = mock(OrganizationService.class);
		userService = new UserService(userRepository, organizationService);
		userFactory = new UserFactory();
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

	@Test
	void testUpdatePassword_UserNotFound() {
		AuthenticationDTO authDTO = new AuthenticationDTO("nonExistingUser", "newPassword");

		when(userRepository.findUserByLogin(authDTO.login())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.updatePassword(authDTO));
		verify(userRepository, never()).save(any(UserModel.class));
	}

	@Test
	void testUserControllerConstructor() {
		UserService mockService = mock(UserService.class);
		UserController controller = new UserController(mockService);

		assertEquals(mockService, controller.userService);
	}

	@Test
	void testBuildUserDTO() {
		OrganizationModel organization = mock(OrganizationModel.class);
		UserModel userModel = new UserModel();
		userModel.setId(1L);
		userModel.setLogin("testUser");
		userModel.setRole(UserRole.ADMIN);
		userModel.setOrganization(organization);
		userModel.setCreationDate(LocalDate.now());
		userModel.setActive(true);

		UserDTO userDTO = userFactory.build(userModel);

		assertEquals(userModel.getId(), userDTO.getId());
		assertEquals(userModel.getLogin(), userDTO.getLogin());
		assertEquals(userModel.getRole(), userDTO.getRole());
		assertEquals(userModel.isActive(), userDTO.isActive());
		assertEquals(userModel.getCreationDate(), userDTO.getCreationDate());
	}

	@Test
	void testBuildUserDTOList() {
		OrganizationModel organization = mock(OrganizationModel.class);
		UserModel user1 = new UserModel();
		user1.setId(1L);
		user1.setLogin("user1");
		user1.setRole(UserRole.ADMIN);
		user1.setOrganization(organization);
		user1.setCreationDate(LocalDate.now());
		user1.setActive(true);

		UserModel user2 = new UserModel();
		user2.setId(2L);
		user2.setLogin("user2");
		user2.setRole(UserRole.TECHNICIAN);
		user2.setOrganization(organization);
		user2.setCreationDate(LocalDate.now());
		user2.setActive(false);

		List<UserModel> userModels = Arrays.asList(user1, user2);

		List<UserDTO> userDTOs = userFactory.build(userModels);

		assertEquals(2, userDTOs.size());
		assertEquals(user1.getId(), userDTOs.get(0).getId());
		assertEquals(user2.getId(), userDTOs.get(1).getId());
	}

	@Test
	void testBuildUpdateUserModel() {
		OrganizationModel organization = mock(OrganizationModel.class);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setLogin("updatedUser");
		userDTO.setRole(UserRole.USER);
		userDTO.setActive(false);

		UserModel userModel = new UserModel();
		userModel.setId(1L);
		userModel.setLogin("originalUser");
		userModel.setRole(UserRole.ADMIN);
		userModel.setActive(true);
		userModel.setOrganization(mock(OrganizationModel.class));

		UserModel updatedUserModel = userFactory.buildUpdate(userModel, userDTO, organization);

		assertEquals(userDTO.getId(), updatedUserModel.getId());
		assertEquals(userDTO.getLogin(), updatedUserModel.getLogin());
		assertEquals(userDTO.getRole(), updatedUserModel.getRole());
		assertEquals(userDTO.isActive(), updatedUserModel.isActive());
		assertEquals(organization, updatedUserModel.getOrganization());
	}

}
