package br.com.api.techvisit.technician;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.technician.definition.TechnicianSaveDTO;
import br.com.api.techvisit.technician.exception.TechnicianNotFoundException;
import br.com.api.techvisit.user.UserService;
import br.com.api.techvisit.user.definition.UserModel;
import br.com.api.techvisit.user.exception.LoginAlreadyExistsException;

class TechnicianTest {

	@InjectMocks
	private TechnicianService technicianService;

	@Mock
	private TechnicianRepository technicianRepository;

	@Mock
	private OrganizationService organizationService;

	@Mock
	private UserService userService;

	private TechnicianModel technicianModel;
	private TechnicianDTO technicianDTO;
	private TechnicianSaveDTO technicianSaveDTO;
	private OrganizationModel organizationModel;
	private UserModel userModel;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		organizationModel = TechnicianTestHelper.createOrganizationModel();
		userModel = TechnicianTestHelper.createUserModel();
		technicianModel = TechnicianTestHelper.createTechnicianModel();
		technicianDTO = TechnicianTestHelper.createTechnicianDTO();
		technicianSaveDTO = TechnicianTestHelper.createTechnicianSaveDTO();
	}

	@Test
	void testGetAllTechnicians() {
		List<TechnicianModel> technicianModels = Collections.singletonList(technicianModel);
		when(technicianRepository.findAllByOrganizationId(1L)).thenReturn(technicianModels);

		List<TechnicianDTO> result = technicianService.getAll(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(technicianDTO.getName(), result.get(0).getName());
		verify(technicianRepository, times(1)).findAllByOrganizationId(1L);
	}

	@Test
	void testSaveTechnician_Success() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(userService.saveTechnician(anyString(), anyString(), anyBoolean(), any(OrganizationModel.class)))
				.thenReturn(userModel);
		when(technicianRepository.save(any(TechnicianModel.class))).thenReturn(technicianModel);

		TechnicianDTO result = technicianService.save(technicianSaveDTO);

		assertNotNull(result);
		assertEquals(technicianDTO.getName(), result.getName());
		verify(organizationService, times(1)).getOrganizationById(1L);
		verify(userService, times(1)).saveTechnician(anyString(), anyString(), anyBoolean(),
				any(OrganizationModel.class));
		verify(technicianRepository, times(1)).save(any(TechnicianModel.class));
	}

	@Test
	void testSaveTechnician_OrganizationNotFound() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.empty());

		assertThrows(OrganizationNotFoundException.class, () -> {
			technicianService.save(technicianSaveDTO);
		});

		verify(organizationService, times(1)).getOrganizationById(1L);
		verifyNoInteractions(userService);
		verifyNoInteractions(technicianRepository);
	}

	@Test
	void testSaveTechnician_LoginAlreadyExists() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(userService.saveTechnician(anyString(), anyString(), anyBoolean(), any(OrganizationModel.class)))
				.thenThrow(new LoginAlreadyExistsException("Login already exists"));

		assertThrows(LoginAlreadyExistsException.class, () -> {
			technicianService.save(technicianSaveDTO);
		});

		verify(organizationService, times(1)).getOrganizationById(1L);
		verify(userService, times(1)).saveTechnician(anyString(), anyString(), anyBoolean(),
				any(OrganizationModel.class));
		verifyNoInteractions(technicianRepository);
	}

	@Test
	void testUpdateTechnician_Success() {
		when(technicianRepository.findById(1L)).thenReturn(Optional.of(technicianModel));
		doNothing().when(userService).updatePasswordAndActive(anyString(), anyString(), anyBoolean());
		when(technicianRepository.save(any(TechnicianModel.class))).thenReturn(technicianModel);

		technicianService.updateTechnician(1L, technicianSaveDTO);

		assertEquals(technicianSaveDTO.getName(), technicianModel.getName());
		verify(technicianRepository, times(1)).findById(1L);
		verify(userService, times(1)).updatePasswordAndActive(anyString(), anyString(), anyBoolean());
		verify(technicianRepository, times(1)).save(any(TechnicianModel.class));
	}

	@Test
	void testUpdateTechnician_TechnicianNotFound() {
		when(technicianRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(TechnicianNotFoundException.class, () -> {
			technicianService.updateTechnician(1L, technicianSaveDTO);
		});

		verify(technicianRepository, times(1)).findById(1L);
		verifyNoInteractions(userService);
	}

	@Test
	void testGetTechnicianById_Success() {
		when(technicianRepository.findById(1L)).thenReturn(Optional.of(technicianModel));

		Optional<TechnicianModel> result = technicianService.getTechnicianById(1L);

		assertTrue(result.isPresent());
		assertEquals(technicianModel.getId(), result.get().getId());
		verify(technicianRepository, times(1)).findById(1L);
	}

	@Test
	void testGetTechnicianById_NotFound() {
		when(technicianRepository.findById(1L)).thenReturn(Optional.empty());

		Optional<TechnicianModel> result = technicianService.getTechnicianById(1L);

		assertFalse(result.isPresent());
		verify(technicianRepository, times(1)).findById(1L);
	}

}
