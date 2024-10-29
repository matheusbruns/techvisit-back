package br.com.api.techvisit.authentication;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.api.techvisit.security.SecurityFilter;
import br.com.api.techvisit.security.TokenService;
import br.com.api.techvisit.user.UserRepository;
import br.com.api.techvisit.user.definition.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class SecurityFilterTest {

	@InjectMocks
	private SecurityFilter securityFilter;

	@Mock
	private TokenService tokenService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private HttpServletRequest mockRequest;

	@Mock
	private HttpServletResponse mockResponse;

	@Mock
	private FilterChain mockFilterChain;

	private UserModel userModel;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		userModel = new UserModel();
		userModel.setLogin("user1");
		userModel.setPassword("password");
		userModel.setActive(true);

		when(mockRequest.getRequestURI()).thenReturn("/someProtectedEndpoint");
		when(mockRequest.getMethod()).thenReturn("GET");
		when(mockRequest.getContextPath()).thenReturn("");
	}

	@AfterEach
	void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void testDoFilter_NoToken() throws ServletException, IOException {
		when(mockRequest.getHeader("Authorization")).thenReturn(null);

		securityFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

		assertNull(SecurityContextHolder.getContext().getAuthentication());
		verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
	}

	@Test
	void testDoFilter_InvalidToken() throws ServletException, IOException {
		when(mockRequest.getHeader("Authorization")).thenReturn("Bearer invalidtoken");
		when(tokenService.validateToken("invalidtoken")).thenReturn(null);

		securityFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

		assertNull(SecurityContextHolder.getContext().getAuthentication());
		verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
	}

	@Test
	void testDoFilter_ValidToken_UserExists() throws ServletException, IOException {
		when(mockRequest.getHeader("Authorization")).thenReturn("Bearer validtoken");
		when(tokenService.validateToken("validtoken")).thenReturn("user1");
		when(userRepository.findByLogin("user1")).thenReturn(Optional.of(userModel));

		securityFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

		var authentication = SecurityContextHolder.getContext().getAuthentication();
		assertNotNull(authentication);
		assertEquals("user1", ((UserDetails) authentication.getPrincipal()).getUsername());
		verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
	}

	@Test
	void testDoFilter_ValidToken_UserDoesNotExist() throws ServletException, IOException {
		when(mockRequest.getHeader("Authorization")).thenReturn("Bearer validtoken");
		when(tokenService.validateToken("validtoken")).thenReturn("user1");
		when(userRepository.findByLogin("user1")).thenReturn(Optional.empty());

		securityFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

		assertNull(SecurityContextHolder.getContext().getAuthentication());
		verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
	}

	@Test
	void testDoFilter_InvalidAuthorizationHeader() throws ServletException, IOException {
		when(mockRequest.getHeader("Authorization")).thenReturn("InvalidHeaderValue");

		securityFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

		assertNull(SecurityContextHolder.getContext().getAuthentication());
		verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
	}
}
