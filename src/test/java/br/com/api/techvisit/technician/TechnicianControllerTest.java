package br.com.api.techvisit.technician;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianSaveDTO;
import br.com.api.techvisit.user.exception.LoginAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class TechnicianControllerTest {

	@InjectMocks
	private TechnicianController technicianController;

	@Mock
	private TechnicianService technicianService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(technicianController).alwaysDo(print()).build();

		objectMapper = new ObjectMapper();
	}

	@Test
	void testGetAllTechnicians() throws Exception {
		Long organizationId = 1L;
		TechnicianDTO technicianDTO = new TechnicianDTO();
		List<TechnicianDTO> technicians = Collections.singletonList(technicianDTO);

		when(technicianService.getAll(organizationId)).thenReturn(technicians);

		mockMvc.perform(get("/technician/get-all").param("organization", organizationId.toString()))
				.andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(technicians)));
	}

	@Test
	void testSaveTechnician_Success() throws Exception {
		TechnicianSaveDTO technicianSaveDTO = new TechnicianSaveDTO();
		TechnicianDTO technicianDTO = new TechnicianDTO();

		when(technicianService.save(technicianSaveDTO)).thenReturn(technicianDTO);

		mockMvc.perform(post("/technician").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(technicianSaveDTO))).andExpect(status().isOk())
				.andExpect(content().string(""));
	}

	@Test
	void testSaveTechnician_LoginAlreadyExists() throws Exception {
		TechnicianSaveDTO technicianSaveDTO = new TechnicianSaveDTO();

		doThrow(new LoginAlreadyExistsException("Login already exists")).when(technicianService)
				.save(technicianSaveDTO);

		mockMvc.perform(post("/technician").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(technicianSaveDTO)))
				.andExpect(status().isConflict()).andExpect(content().string("Login already exists"));
	}

	@Test
	void testUpdateTechnician() throws Exception {
		Long technicianId = 1L;
		TechnicianSaveDTO technicianSaveDTO = new TechnicianSaveDTO();

		doNothing().when(technicianService).updateTechnician(technicianId, technicianSaveDTO);

		mockMvc.perform(put("/technician/{id}", technicianId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(technicianSaveDTO))).andExpect(status().isOk());
	}
	
	@Test
	void testTechnicianControllerConstructor() {
		TechnicianService technicianServiceMock = mock(TechnicianService.class);
		TechnicianController technicianControllerMock = new TechnicianController(technicianServiceMock);
		assertEquals(technicianServiceMock, technicianControllerMock.technicianService);
	}

}
