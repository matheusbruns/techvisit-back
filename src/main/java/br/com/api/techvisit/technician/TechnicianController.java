package br.com.api.techvisit.technician;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianSaveDTO;
import br.com.api.techvisit.user.exception.LoginAlreadyExistsException;

@RestController
@RequestMapping("/technician")
public class TechnicianController {

	private final TechnicianService technicianService;

	public TechnicianController(TechnicianService technicianService) {
		this.technicianService = technicianService;
	}

	@GetMapping("/get-all")
	public List<TechnicianDTO> getAllTechnicians(@RequestParam("organization") Long organizationId) {
		return this.technicianService.getAll(organizationId);
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody TechnicianSaveDTO dto) {
		try {
			this.technicianService.save(dto);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (LoginAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @RequestBody TechnicianSaveDTO dto) {
		technicianService.updateTechnician(id, dto);
	}

}
