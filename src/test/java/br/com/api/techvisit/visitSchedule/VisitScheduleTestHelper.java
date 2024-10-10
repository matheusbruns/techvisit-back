package br.com.api.techvisit.visitSchedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.api.techvisit.customer.definition.CustomerDTO;
import br.com.api.techvisit.customer.definition.CustomerModel;
import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.user.definition.UserModel;
import br.com.api.techvisit.user.definition.UserRole;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleDTO;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleModel;
import br.com.api.techvisit.visitschedule.definition.VisitStatus;

public class VisitScheduleTestHelper {

	public static VisitScheduleDTO createVisitScheduleDTO() {
		VisitScheduleDTO dto = new VisitScheduleDTO();
		dto.setId(1L);
		dto.setDescription("Visita técnica para manutenção");
		dto.setCustomer(createCustomerDTO());
		dto.setTechnician(createTechnicianDTO());
		dto.setCity("Joinville");
		dto.setState("SC");
		dto.setNeighborhood("Centro");
		dto.setStreet("Rua das Palmeiras");
		dto.setNumber("200");
		dto.setComplement("Casa");
		dto.setCep("02000-000");
		dto.setPrice(BigDecimal.valueOf(100.00));
		dto.setStartDate(String.valueOf(System.currentTimeMillis()));
		dto.setEndDate(String.valueOf(System.currentTimeMillis() + 3600000));
		dto.setComment("Cliente prefere atendimento pela manhã.");
		dto.setOrganization(createOrganizationDTO());
		dto.setStatus(VisitStatus.SCHEDULED);
		return dto;
	}

	public static VisitScheduleModel createVisitScheduleModel() {
		VisitScheduleModel model = new VisitScheduleModel();
		model.setId(1L);
		model.setDescription("Visita técnica para manutenção");
		model.setCustomer(createCustomerModel());
		model.setTechnician(createTechnicianModel());
		model.setCity("Joinville");
		model.setState("SC");
		model.setNeighborhood("Centro");
		model.setStreet("Rua das Palmeiras");
		model.setNumber("200");
		model.setComplement("Casa");
		model.setCep("02000-000");
		model.setPrice(BigDecimal.valueOf(100.00));
		model.setStartDate(LocalDateTime.now());
		model.setEndDate(LocalDateTime.now().plusHours(1));
		model.setComment("Cliente prefere atendimento pela manhã.");
		model.setOrganization(createOrganizationModel());
		model.setStatus(VisitStatus.SCHEDULED);
		return model;
	}

	public static OrganizationDTO createOrganizationDTO() {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(1L);
		dto.setExternalCode("EXT123");
		dto.setName("TechVisit Organization");
		dto.setCreationDate(LocalDate.now());
		dto.setExpirationDate(LocalDate.now().plusYears(1));
		return dto;
	}

	public static OrganizationModel createOrganizationModel() {
		OrganizationModel model = new OrganizationModel();
		model.setId(1L);
		model.setExternalCode("TECHVISIT");
		model.setName("TechVisit");
		model.setCreationDate(LocalDate.now());
		model.setExpirationDate(LocalDate.now().plusYears(1));
		return model;
	}

	public static CustomerDTO createCustomerDTO() {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(1L);
		dto.setFirstName("Matheus");
		dto.setLastName("Bruns");
		dto.setCpf("123.456.789-00");
		dto.setPhoneNumber("(47)99999-9999");
		dto.setState("SC");
		dto.setCity("Joinville");
		dto.setNeighborhood("Centro");
		dto.setStreet("Rua das Palmeiras");
		dto.setNumber("200");
		dto.setComplement("Casa");
		dto.setCep("01000-000");
		dto.setOrganization(createOrganizationDTO());
		return dto;
	}

	public static CustomerModel createCustomerModel() {
		CustomerModel model = new CustomerModel();
		model.setId(1L);
		model.setFirstName("Matheus");
		model.setLastName("Bruns");
		model.setCpf("123.456.789-00");
		model.setPhoneNumber("(47)99999-9999");
		model.setState("SC");
		model.setCity("Joinville");
		model.setNeighborhood("Centro");
		model.setStreet("Rua das Palmeiras");
		model.setNumber("200");
		model.setComplement("Casa");
		model.setCep("01000-000");
		model.setOrganization(createOrganizationModel());
		return model;
	}

	public static TechnicianDTO createTechnicianDTO() {
		TechnicianDTO dto = new TechnicianDTO();
		dto.setId(1L);
		dto.setName("Rainer");
		dto.setLogin("rainer.bruns");
		dto.setCpf("987.654.321-00");
		dto.setEmail("rainer@test.com");
		dto.setPhoneNumber("11988888888");
		dto.setActive(true);
		return dto;
	}

	public static TechnicianModel createTechnicianModel() {
		TechnicianModel model = new TechnicianModel();
		model.setId(1L);
		model.setName("Rainer");
		model.setUser(createUserModel());
		model.setCpf("987.654.321-00");
		model.setEmail("rainer@test.com");
		model.setPhoneNumber("11988888888");
		return model;
	}

	public static UserModel createUserModel() {
		UserModel model = new UserModel();
		model.setId(1L);
		model.setLogin("rainer.bruns");
		model.setRole(UserRole.TECHNICIAN);
		model.setPassword("senhaSegura");
		model.setActive(true);
		model.setCreationDate(LocalDate.now());
		return model;
	}

}
