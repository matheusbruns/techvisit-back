package br.com.api.techvisit.technician;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.factory.TechnicianFactory;

@Service
public class TechnicianService {

	@Autowired
	private TechnicianRepository technicianRepository;

	public List<TechnicianDTO> getAll(Long organizationId) {
		return new TechnicianFactory().build(this.technicianRepository.findAllByOrganizationId(organizationId));
	}

}
