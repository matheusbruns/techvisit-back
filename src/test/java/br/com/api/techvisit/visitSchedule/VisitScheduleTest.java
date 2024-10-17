package br.com.api.techvisit.visitSchedule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.empty());
		assertThrows(OrganizationNotFoundException.class, () -> visitScheduleService.save(visitScheduleDTO));
	}

	@Test
	void testSaveThrowsCustomerNotFoundException() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(customerService.getCustomerById(1L)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> visitScheduleService.save(visitScheduleDTO));
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
		when(visitScheduleRepository.findById(1L)).thenReturn(Optional.of(visitScheduleModel));
		when(visitScheduleRepository.save(any(VisitScheduleModel.class))).thenReturn(visitScheduleModel);

		VisitScheduleDTO updatedDTO = visitScheduleService.editStatus(1L, VisitStatus.ATTENDED);
		assertEquals(VisitStatus.ATTENDED, updatedDTO.getStatus());
	}

	@Test
	void testEditStatusThrowsVisitScheduleNotFoundException() {
		when(visitScheduleRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(VisitScheduleNotFoundException.class,
				() -> visitScheduleService.editStatus(1L, VisitStatus.ATTENDED));
	}

	@Test
	void testDeleteSuccess() {
		doNothing().when(visitScheduleRepository).deleteAllByIdInBatch(any());
		assertDoesNotThrow(() -> visitScheduleService.delete(List.of(1L)));
		verify(visitScheduleRepository, times(1)).deleteAllByIdInBatch(any());
	}

}
