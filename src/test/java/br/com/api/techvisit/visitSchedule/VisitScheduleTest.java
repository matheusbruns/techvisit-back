package br.com.api.techvisit.visitSchedule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.api.techvisit.customer.CustomerService;
import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.customer.exception.CustomerNotFoundException;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.technician.TechnicianService;
import br.com.api.techvisit.technician.exception.TechnicianNotFoundException;
import br.com.api.techvisit.visitschedule.VisitScheduleRepository;
import br.com.api.techvisit.visitschedule.VisitScheduleService;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleDTO;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleModel;
import br.com.api.techvisit.visitschedule.definition.VisitStatus;
import br.com.api.techvisit.visitschedule.exception.VisitScheduleNotFoundException;

@ExtendWith(MockitoExtension.class)
class VisitScheduleTest {

	@Mock
	private VisitScheduleRepository visitScheduleRepository;

	@Mock
	private CustomerService customerService;

	@Mock
	private TechnicianService technicianService;

	@Mock
	private OrganizationService organizationService;

	@InjectMocks
	private VisitScheduleService visitScheduleService;

	private VisitScheduleDTO visitScheduleDTO;
	private VisitScheduleModel visitScheduleModel;
	private OrganizationModel organizationModel;
	private CustomerModel customerModel;

	@BeforeEach
	void setUp() {
		visitScheduleDTO = VisitScheduleTestHelper.createVisitScheduleDTO();
		visitScheduleModel = VisitScheduleTestHelper.createVisitScheduleModel();
		organizationModel = VisitScheduleTestHelper.createOrganizationModel();
		customerModel = VisitScheduleTestHelper.createCustomerModel();
	}

	@Test
	void testGetAll() {
		when(visitScheduleRepository.findAllByOrganizationId(1L)).thenReturn(List.of(visitScheduleModel));
		List<VisitScheduleDTO> result = visitScheduleService.getAll(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(visitScheduleRepository, times(1)).findAllByOrganizationId(1L);
	}

	@Test
	void testSaveSuccess() {
		when(organizationService.getOrganizationById(1L))
				.thenReturn(Optional.of(VisitScheduleTestHelper.createOrganizationModel()));
		when(customerService.getCustomerById(1L))
				.thenReturn(Optional.of(VisitScheduleTestHelper.createCustomerModel()));
		when(technicianService.getTechnicianById(1L))
				.thenReturn(Optional.of(VisitScheduleTestHelper.createTechnicianModel()));
		when(visitScheduleRepository.save(any(VisitScheduleModel.class))).thenReturn(visitScheduleModel);

		assertDoesNotThrow(() -> visitScheduleService.save(visitScheduleDTO));
		verify(visitScheduleRepository, times(1)).save(any(VisitScheduleModel.class));
	}

	@Test
	void testSaveThrowsOrganizationNotFoundException() {
		assertThrows(OrganizationNotFoundException.class, () -> visitScheduleService.save(visitScheduleDTO));

		verify(organizationService, times(1)).getOrganizationById(1L);
		verifyNoInteractions(customerService);
		verifyNoInteractions(technicianService);
		verifyNoInteractions(visitScheduleRepository);
	}

	@Test
	void testSaveThrowsCustomerNotFoundException() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(customerService.getCustomerById(1L)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> visitScheduleService.save(visitScheduleDTO));

		verify(organizationService, times(1)).getOrganizationById(1L);
		verify(customerService, times(1)).getCustomerById(1L);
		verifyNoInteractions(technicianService);
		verifyNoInteractions(visitScheduleRepository);
	}

	@Test
	void testSaveThrowsTechnicianNotFoundException() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customerModel));
		when(technicianService.getTechnicianById(1L)).thenReturn(Optional.empty());

		assertThrows(TechnicianNotFoundException.class, () -> visitScheduleService.save(visitScheduleDTO));
	}

	@Test
	void testEditStatusSuccess() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customerModel));
		when(technicianService.getTechnicianById(1L)).thenReturn(Optional.empty());

		assertThrows(TechnicianNotFoundException.class, () -> visitScheduleService.save(visitScheduleDTO));

		verify(organizationService, times(1)).getOrganizationById(1L);
		verify(customerService, times(1)).getCustomerById(1L);
		verify(technicianService, times(1)).getTechnicianById(1L);
		verifyNoInteractions(visitScheduleRepository);
	}

	@Test
	void testEditStatusThrowsVisitScheduleNotFoundException() {
		when(visitScheduleRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(VisitScheduleNotFoundException.class,
				() -> visitScheduleService.editStatus(1L, VisitStatus.ATTENDED));

		verify(visitScheduleRepository, times(1)).findById(1L);
		verify(visitScheduleRepository, times(0)).save(any(VisitScheduleModel.class));
	}

	@Test
	void testGetAllVisitSchedulesByUser() {
		when(visitScheduleRepository.findAllByOrganizationIdAndUserId(1L, 1L)).thenReturn(List.of(visitScheduleModel));

		List<VisitScheduleDTO> result = visitScheduleService.getAllByUserId(1L, 1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(visitScheduleRepository, times(1)).findAllByOrganizationIdAndUserId(1L, 1L);
	}

	@Test
	void testUpdateVisitSuccess() {
		when(visitScheduleRepository.findById(1L)).thenReturn(Optional.of(visitScheduleModel));
		when(visitScheduleRepository.save(any(VisitScheduleModel.class))).thenReturn(visitScheduleModel);

		VisitScheduleDTO updatedDTO = new VisitScheduleDTO();
		updatedDTO.setId(1L);
		updatedDTO.setPrice(BigDecimal.valueOf(100));
		updatedDTO.setComment("New comment");

		VisitScheduleDTO result = visitScheduleService.updateVisit(updatedDTO);

		assertNotNull(result);
		assertEquals(BigDecimal.valueOf(100), result.getPrice());
		assertEquals("New comment", result.getComment());
		assertEquals(VisitStatus.ATTENDED, result.getStatus());
		verify(visitScheduleRepository, times(1)).findById(1L);
		verify(visitScheduleRepository, times(1)).save(any(VisitScheduleModel.class));
	}

	@Test
	void testUpdateVisitThrowsVisitScheduleNotFoundException() {
		when(visitScheduleRepository.findById(1L)).thenReturn(Optional.empty());

		VisitScheduleDTO updatedDTO = new VisitScheduleDTO();
		updatedDTO.setId(1L);
		updatedDTO.setPrice(BigDecimal.valueOf(100));
		updatedDTO.setComment("New comment");

		assertThrows(VisitScheduleNotFoundException.class, () -> visitScheduleService.updateVisit(updatedDTO));
		verify(visitScheduleRepository, times(1)).findById(1L);
		verify(visitScheduleRepository, times(0)).save(any(VisitScheduleModel.class));
	}

	@Test
	void testDeleteSuccess() {
		doNothing().when(visitScheduleRepository).deleteAllByIdInBatch(any());
		assertDoesNotThrow(() -> visitScheduleService.delete(List.of(1L)));
		verify(visitScheduleRepository, times(1)).deleteAllByIdInBatch(any());
	}
}
