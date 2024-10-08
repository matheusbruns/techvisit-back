package br.com.api.techvisit.organization;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.techvisit.organization.definition.OrganizationDTO;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

	private final OrganizationService organizationService;

	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrganizationDTO> getAll() {
		return this.organizationService.getAll();
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public OrganizationDTO save(@RequestBody OrganizationDTO organizationBean) {
		return this.organizationService.save(organizationBean);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public OrganizationDTO update(@RequestBody OrganizationDTO organizationBean) {
		return this.organizationService.update(organizationBean);
	}

}
