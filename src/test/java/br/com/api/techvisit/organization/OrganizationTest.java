package br.com.api.techvisit.organization;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.definition.OrganizationResponseDTO;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrganizationTest {

	@InjectMocks
	private OrganizationController organizationController;

	@Mock
	private OrganizationService organizationServiceMock;

	private MockMvc mockMvc;

	@InjectMocks
	private OrganizationService organizationService;

	@Mock
	private OrganizationRepository organizationRepositoryMock;

	private OrganizationFactory organizationFactory = new OrganizationFactory();

	private OrganizationDTO testOrganizationDTO;
	private OrganizationModel testOrganizationModel;

	@BeforeEach
	void setUpController() {
		mockMvc = MockMvcBuilders.standaloneSetup(organizationController).build();
		organizationFactory = new OrganizationFactory();
		testOrganizationDTO = OrganizationTestHelper.createOrganizationDTO();
		testOrganizationModel = OrganizationTestHelper.createOrganizationModel();
	}

	@Test
	void testGetAll() throws Exception {
		OrganizationDTO org1 = new OrganizationDTO();
		org1.setId(1L);
		org1.setName("Organization 1");
		org1.setExternalCode("ORG1");
		org1.setCreationDate(LocalDate.now());

		OrganizationDTO org2 = new OrganizationDTO();
		org2.setId(2L);
		org2.setName("Organization 2");
		org2.setExternalCode("ORG2");
		org2.setCreationDate(LocalDate.now());

		List<OrganizationDTO> organizations = Arrays.asList(org1, org2);

		when(organizationServiceMock.getAll()).thenReturn(organizations);

		mockMvc.perform(get("/organization").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].id").value(1L))
				.andExpect(jsonPath("$[0].name").value("Organization 1")).andExpect(jsonPath("$[1].id").value(2L))
				.andExpect(jsonPath("$[1].name").value("Organization 2"));

		verify(organizationServiceMock, times(1)).getAll();
	}

	@Test
	void testServiceGetAll() {
		when(organizationRepositoryMock.findAll()).thenReturn(Arrays.asList(testOrganizationModel));

		List<OrganizationDTO> result = organizationService.getAll();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Organization 1", result.get(0).getName());

		verify(organizationRepositoryMock, times(1)).findAll();
	}

	@Test
	void testServiceSave() {
		when(organizationRepositoryMock.save(any(OrganizationModel.class))).thenReturn(testOrganizationModel);

		OrganizationDTO result = organizationService.save(testOrganizationDTO);

		assertNotNull(result);
		assertEquals("Organization 1", result.getName());

		verify(organizationRepositoryMock, times(1)).save(any(OrganizationModel.class));
	}

	@Test
	void testServiceUpdate_Success() {
		when(organizationRepositoryMock.existsById(1L)).thenReturn(true);
		when(organizationRepositoryMock.save(any(OrganizationModel.class))).thenReturn(testOrganizationModel);

		OrganizationDTO result = organizationService.update(testOrganizationDTO);

		assertNotNull(result);
		assertEquals("Organization 1", result.getName());

		verify(organizationRepositoryMock, times(1)).existsById(1L);
		verify(organizationRepositoryMock, times(1)).save(any(OrganizationModel.class));
	}

	@Test
	void testServiceUpdate_OrganizationNotFound() {
		when(organizationRepositoryMock.existsById(1L)).thenReturn(false);

		assertThrows(OrganizationNotFoundException.class, () -> {
			organizationService.update(testOrganizationDTO);
		});

		verify(organizationRepositoryMock, times(1)).existsById(1L);
		verify(organizationRepositoryMock, times(0)).save(any(OrganizationModel.class));
	}

	@Test
	void testServiceGetOrganizationById() {
		when(organizationRepositoryMock.findById(1L)).thenReturn(Optional.of(testOrganizationModel));

		Optional<OrganizationModel> result = organizationService.getOrganizationById(1L);

		assertTrue(result.isPresent());
		assertEquals("Organization 1", result.get().getName());

		verify(organizationRepositoryMock, times(1)).findById(1L);
	}

	@Test
	void testFactoryBuildDTOFromModel() {
		OrganizationModel model = new OrganizationModel();
		model.setId(1L);
		model.setExternalCode("ORG1");
		model.setName("Organization 1");
		model.setCreationDate(LocalDate.now());
		model.setExpirationDate(LocalDate.now().plusDays(30));

		OrganizationDTO dto = OrganizationTestHelper.createOrganizationDTO();

		assertNotNull(dto);
		assertEquals(1L, dto.getId());
		assertEquals("ORG1", dto.getExternalCode());
		assertEquals("Organization 1", dto.getName());
	}

	@Test
	void testFactoryBuildListDTOFromModels() {
		OrganizationModel model1 = new OrganizationModel();
		model1.setId(1L);
		model1.setExternalCode("ORG1");
		model1.setName("Organization 1");

		OrganizationModel model2 = new OrganizationModel();
		model2.setId(2L);
		model2.setExternalCode("ORG2");
		model2.setName("Organization 2");

		List<OrganizationDTO> dtos = organizationFactory.build(Arrays.asList(model1, model2));

		assertNotNull(dtos);
		assertEquals(2, dtos.size());
		assertEquals("Organization 1", dtos.get(0).getName());
		assertEquals("Organization 2", dtos.get(1).getName());
	}

	@Test
	void testFactoryBuildModelFromDTO() {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(1L);
		dto.setExternalCode("ORG1");
		dto.setName("Organization 1");
		dto.setCreationDate(LocalDate.now());
		dto.setExpirationDate(LocalDate.now().plusDays(30));

		OrganizationModel model = organizationFactory.build(dto);

		assertNotNull(model);
		assertEquals(1L, model.getId());
		assertEquals("ORG1", model.getExternalCode());
		assertEquals("Organization 1", model.getName());
	}

	@Test
	void testFactoryBuildNewModelFromDTO() {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setExternalCode("ORG1");
		dto.setName("Organization 1");
		dto.setExpirationDate(LocalDate.now().plusDays(30));

		OrganizationModel model = organizationFactory.buildNew(dto);

		assertNotNull(model);
		assertNull(model.getId());
		assertEquals("ORG1", model.getExternalCode());
		assertEquals("Organization 1", model.getName());
		assertNotNull(model.getCreationDate());
		assertEquals(LocalDate.now(), model.getCreationDate());
	}

	@Test
	void testFactoryBuildDTOWithParameters() {
		Long id = 1L;
		String externalCode = "ORG1";
		String name = "Organization 1";
		LocalDate creationDate = LocalDate.now();
		LocalDate expirationDate = LocalDate.now().plusDays(30);

		OrganizationDTO dto = organizationFactory.build(id, externalCode, name, creationDate, expirationDate);

		assertNotNull(dto);
		assertEquals(id, dto.getId());
		assertEquals(externalCode, dto.getExternalCode());
		assertEquals(name, dto.getName());
	}

	@Test
	void testFactoryBuildResponse() {
		OrganizationModel model = new OrganizationModel();
		model.setId(1L);
		model.setExternalCode("ORG1");
		model.setName("Organization 1");

		OrganizationResponseDTO responseDTO = organizationFactory.buildResponse(model);

		assertNotNull(responseDTO);
		assertEquals(1L, responseDTO.getId());
		assertEquals("ORG1", responseDTO.getExternalCode());
		assertEquals("Organization 1", responseDTO.getName());
	}
}