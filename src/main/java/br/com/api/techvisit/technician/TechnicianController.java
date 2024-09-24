package br.com.api.techvisit.technician;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.technician.definition.TechnicianDTO;

@RestController
@RequestMapping("/technician")
public class TechnicianController {

	@Autowired
	private TechnicianService technicianService;
	
	@GetMapping("/get-all")
	public List<TechnicianDTO> getAllTechnicians(@RequestParam("organization") Long organizationId) {
		return this.technicianService.getAll(organizationId);
	}

}
