package br.com.api.techvisit.visitschedule;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.visitschedule.definition.VisitScheduleDTO;

@RestController
@RequestMapping("/visit-schedule")
public class VisitScheduleController {

	private final VisitScheduleService visitScheduleService;

	public VisitScheduleController(VisitScheduleService visitScheduleService) {
		this.visitScheduleService = visitScheduleService;
	}

	@GetMapping
	public List<VisitScheduleDTO> getAll(@RequestParam("organization") Long organizationId) {
		return this.visitScheduleService.getAll(organizationId);
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody VisitScheduleDTO dto) {
		try {
			this.visitScheduleService.save(dto);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping()
	@ResponseStatus(HttpStatus.OK)
	public void delete(@RequestBody List<Long> ids) {
		this.visitScheduleService.delete(ids);
	}

}
