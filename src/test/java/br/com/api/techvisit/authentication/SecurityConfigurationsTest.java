package br.com.api.techvisit.authentication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.com.api.techvisit.authentication.definition.AuthenticationDTO;
import br.com.api.techvisit.authentication.definition.LoginResponseDTO;
import br.com.api.techvisit.authentication.definition.RegisterDTO;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.security.SecurityConfigurations;
import br.com.api.techvisit.security.TokenService;
import br.com.api.techvisit.user.UserRepository;
import br.com.api.techvisit.user.UserService;
import br.com.api.techvisit.user.definition.UserResponseDTO;
import br.com.api.techvisit.user.definition.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfigurations.class)
class SecurityConfigurationsTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthenticationService authenticationService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private TokenService tokenService;

	@MockBean
	private OrganizationService organizationService;

	@MockBean
	private UserService userService;

	@Test
	@DisplayName("Deve permitir login sem autenticação")
	void shouldAllowLogin() throws Exception {
		when(authenticationService.login(any(AuthenticationDTO.class)))
				.thenReturn(new LoginResponseDTO(new UserResponseDTO(1L, "user", UserRole.USER, null, true), "token"));

		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\"login\":\"user\",\"password\":\"password\"}")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Deve negar acesso a endpoint protegido sem autenticação")
	void shouldDenyAccessWithoutAuthentication() throws Exception {
		mockMvc.perform(get("/user/get-all")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Deve permitir acesso a ADMIN em /user/get-all")
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldAllowAdminAccessToUserGetAll() throws Exception {
		mockMvc.perform(get("/user/get-all")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Deve negar acesso a USER em /user/get-all")
	@WithMockUser(username = "user", roles = { "USER" })
	void shouldDenyUserAccessToUserGetAll() throws Exception {
		mockMvc.perform(get("/user/get-all")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Deve negar acesso a USER em /my-visits")
	@WithMockUser(username = "user", roles = { "USER" })
	void shouldDenyUserAccessToMyVisits() throws Exception {
		mockMvc.perform(get("/my-visits")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Deve permitir ADMIN registrar usuários")
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldAllowAdminToRegisterUsers() throws Exception {
		when(authenticationService.register(any(RegisterDTO.class))).thenReturn(ResponseEntity.ok().build());

		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(
				"{\"login\":\"newuser\",\"password\":\"password\",\"role\":\"USER\",\"organization\":null,\"active\":true}"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Deve negar USER registrar usuários")
	@WithMockUser(username = "user", roles = { "USER" })
	void shouldDenyUserToRegisterUsers() throws Exception {
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(
				"{\"login\":\"newuser\",\"password\":\"password\",\"role\":\"USER\",\"organization\":null,\"active\":true}"))
				.andExpect(status().isForbidden());
	}
}