package br.com.api.techvisit.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.customer.exception.CannotDeleteCustomerException;
import br.com.api.techvisit.customer.factory.CustomerFactory;
import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;

class CustomerTest {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerService customerServiceUnderTest;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private OrganizationService organizationService;

	private CustomerDTO customerDTO;
	private CustomerModel customerModel;
	private OrganizationModel organizationModel;

	private CustomerFactory customerFactory = new CustomerFactory();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		customerDTO = CustomerTestHelper.createCustomerDTO();
		customerModel = CustomerTestHelper.createCustomerModel();
		organizationModel = CustomerTestHelper.createOrganizationModel();
	}

	@Test
	void testGetAllCustomers() {
		List<CustomerDTO> customers = Collections.singletonList(customerDTO);
		when(customerService.getAllByOrganization(1L)).thenReturn(customers);

		List<CustomerDTO> result = customerController.getAllCustomers(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(customerService, times(1)).getAllByOrganization(1L);
	}

	@Test
	void testSaveCustomer() {
		when(customerService.save(any(CustomerDTO.class))).thenReturn(customerDTO);

		CustomerDTO result = customerController.save(customerDTO);

		assertNotNull(result);
		assertEquals(customerDTO.getId(), result.getId());
		verify(customerService, times(1)).save(any(CustomerDTO.class));
	}

	@Test
	void testDeleteCustomers() {
		List<Long> ids = Arrays.asList(1L, 2L);

		doNothing().when(customerService).delete(ids);

		customerController.delete(ids);

		verify(customerService, times(1)).delete(ids);
	}

	@Test
	void testServiceGetAllByOrganization() {
		List<CustomerModel> customerModels = Collections.singletonList(customerModel);
		when(customerRepository.findAllByOrganizationId(1L)).thenReturn(customerModels);

		List<CustomerDTO> result = customerServiceUnderTest.getAllByOrganization(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(customerDTO.getFirstName(), result.get(0).getFirstName());
		verify(customerRepository, times(1)).findAllByOrganizationId(1L);
	}

	@Test
	void testServiceSaveCustomer() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.of(organizationModel));
		when(customerRepository.save(any(CustomerModel.class))).thenReturn(customerModel);

		CustomerDTO result = customerServiceUnderTest.save(customerDTO);

		assertNotNull(result);
		assertEquals(customerDTO.getFirstName(), result.getFirstName());
		verify(customerRepository, times(1)).save(any(CustomerModel.class));
	}

	@Test
	void testServiceSaveCustomer_OrganizationNotFound() {
		when(organizationService.getOrganizationById(1L)).thenReturn(Optional.empty());

		assertThrows(OrganizationNotFoundException.class, () -> {
			customerServiceUnderTest.save(customerDTO);
		});
	}

	@Test
	void testServiceDeleteCustomers() {
		List<Long> ids = Arrays.asList(1L, 2L);

		doNothing().when(customerRepository).deleteAllByIdInBatch(ids);

		customerServiceUnderTest.delete(ids);

		verify(customerRepository, times(1)).deleteAllByIdInBatch(ids);
	}

	@Test
	void testServiceGetCustomerById() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customerModel));

		Optional<CustomerModel> result = customerServiceUnderTest.getCustomerById(1L);

		assertTrue(result.isPresent());
		assertEquals(customerModel.getId(), result.get().getId());
		verify(customerRepository, times(1)).findById(1L);
	}

	@Test
	void testFactoryBuildDTOFromModel() {
		CustomerDTO result = customerFactory.build(customerModel);

		assertNotNull(result);
		assertEquals(customerModel.getId(), result.getId());
		assertEquals(customerModel.getFirstName(), result.getFirstName());
	}

	@Test
	void testFactoryBuildModelFromDTO() {
		CustomerModel result = customerFactory.build(customerDTO);

		assertNotNull(result);
		assertEquals(customerDTO.getId(), result.getId());
		assertEquals(customerDTO.getFirstName(), result.getFirstName());
	}

	@Test
	void testServiceDeleteCustomers_CannotDeleteCustomerException() {
		List<Long> ids = Arrays.asList(1L, 2L);

		doThrow(new DataIntegrityViolationException("Constraint violation")).when(customerRepository)
				.deleteAllByIdInBatch(ids);

		CannotDeleteCustomerException exception = assertThrows(CannotDeleteCustomerException.class,
				() -> customerServiceUnderTest.delete(ids));

		assertEquals("Existem dados vinculados. Não é possível excluir os clientes.", exception.getMessage());

		verify(customerRepository, times(1)).deleteAllByIdInBatch(ids);
	}

}
