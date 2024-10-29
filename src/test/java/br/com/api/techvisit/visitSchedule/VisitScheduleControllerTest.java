package br.com.api.techvisit.visitSchedule;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import br.com.api.techvisit.visitschedule.VisitScheduleController;
import br.com.api.techvisit.visitschedule.VisitScheduleService;
import br.com.api.techvisit.visitschedule.definition.EditStatusRequestDTO;
import br.com.api.techvisit.visitschedule.definition.VisitScheduleDTO;
import br.com.api.techvisit.visitschedule.definition.VisitStatus;

@ExtendWith(MockitoExtension.class)
class VisitScheduleControllerTest {

	@InjectMocks
	private VisitScheduleController visitScheduleController;

	@Mock
	private VisitScheduleService visitScheduleService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(visitScheduleController)
				.alwaysDo(print()).build();

		objectMapper = new ObjectMapper();
	}

	@Test
	void getAll_ShouldReturnVisitSchedules() throws Exception {
		Long organizationId = 1L;
		VisitScheduleDTO visitSchedule = new VisitScheduleDTO();
		List<VisitScheduleDTO> visitSchedules = Collections.singletonList(visitSchedule);

		when(visitScheduleService.getAll(organizationId)).thenReturn(visitSchedules);

		mockMvc.perform(get("/visit-schedule")
				.param("organization",
				organizationId.toString()))
                .andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(visitSchedules)));
	}

	@Test
	void save_ShouldReturnOkStatus() throws Exception {
		VisitScheduleDTO dto = new VisitScheduleDTO();

        mockMvc.perform(post("/visit-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void delete_ShouldReturnOkStatus() throws Exception {
        List<Long> ids = List.of(1L, 2L);

        mockMvc.perform(delete("/visit-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
    }

	@Test
	void editStatus_ShouldReturnUpdatedVisitSchedule() throws Exception {
		EditStatusRequestDTO dto = new EditStatusRequestDTO(1L, VisitStatus.ATTENDED);
		VisitScheduleDTO updatedSchedule = new VisitScheduleDTO();

		when(visitScheduleService.editStatus(dto.visitScheduleId(), dto.status())).thenReturn(updatedSchedule);

        mockMvc.perform(put("/visit-schedule/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(updatedSchedule)));
	}

	@Test
	void getAllByUserId_ShouldReturnVisitSchedulesForUser() throws Exception {
		Long organizationId = 1L;
		Long userId = 2L;
        VisitScheduleDTO visitSchedule = new VisitScheduleDTO();
        List<VisitScheduleDTO> visitSchedules = Collections.singletonList(visitSchedule);

        when(visitScheduleService.getAllByUserId(organizationId, userId)).thenReturn(visitSchedules);

        mockMvc.perform(get("/visit-schedule/my-visits")
                .param("organization", organizationId.toString())
                .param("user", userId.toString()))
                .andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(visitSchedules)));
	}

	@Test
	void updateVisit_ShouldReturnUpdatedVisitSchedule() throws Exception {
		VisitScheduleDTO visitScheduleDTO = new VisitScheduleDTO();
		VisitScheduleDTO updatedSchedule = new VisitScheduleDTO();

        when(visitScheduleService.updateVisit(visitScheduleDTO)).thenReturn(updatedSchedule);

        mockMvc.perform(put("/visit-schedule/my-visits/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visitScheduleDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedSchedule)));
    }

}
