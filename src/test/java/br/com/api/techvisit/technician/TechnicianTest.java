package br.com.api.techvisit.technician;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.user.UserService;

class TechnicianTest {

	@Mock
	private TechnicianRepository technicianRepository;

	@Mock
	private OrganizationService organizationService;

	@Mock
	private UserService userService;

	@InjectMocks
	private TechnicianService technicianService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllTechnicians() {
		Long organizationId = 1L;

		List<TechnicianModel> technicians = Arrays.asList(new TechnicianModel(), new TechnicianModel());

		when(technicianRepository.findAllByOrganizationId(organizationId)).thenReturn(technicians);

		List<TechnicianDTO> result = technicianService.getAll(organizationId);

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(technicianRepository, times(1)).findAllByOrganizationId(organizationId);
	}

}
