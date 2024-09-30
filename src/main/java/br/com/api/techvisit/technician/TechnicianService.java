package br.com.api.techvisit.technician;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.organization.OrganizationService;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.technician.definition.TechnicianDTO;
import br.com.api.techvisit.technician.definition.TechnicianModel;
import br.com.api.techvisit.technician.definition.TechnicianSaveDTO;
import br.com.api.techvisit.technician.exception.TechnicianNotFoundException;
import br.com.api.techvisit.technician.factory.TechnicianFactory;
import br.com.api.techvisit.user.UserService;
import br.com.api.techvisit.user.model.UserModel;

@Service
public class TechnicianService {

	@Autowired
	private TechnicianRepository technicianRepository;
	
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private UserService userService;

	public List<TechnicianDTO> getAll(Long organizationId) {
		return new TechnicianFactory().build(this.technicianRepository.findAllByOrganizationId(organizationId));
	}

	public TechnicianDTO save(TechnicianSaveDTO dto) {
		TechnicianFactory factory = new TechnicianFactory();
		OrganizationModel organization = this.organizationService.getOrganizationById(dto.getOrganization().getId()).orElseThrow(() -> new OrganizationNotFoundException("Organization not found."));

		UserModel user = this.userService.saveTechnician(dto.getLogin(), dto.getPassword(), dto.isActive(), organization);
		return factory.build(this.technicianRepository.save(factory.build(dto, user, organization)));
	}

	public void updateTechnician(Long id, TechnicianSaveDTO dto) {
		TechnicianModel existingTechnician = this.technicianRepository.findById(id).orElseThrow(() -> new TechnicianNotFoundException("Técnico não encontrado."));

		dto.setLogin(existingTechnician.getUser().getLogin());
		existingTechnician.setName(dto.getName());
		existingTechnician.setEmail(dto.getEmail());
		existingTechnician.setPhoneNumber(dto.getPhoneNumber());
		existingTechnician.setCpf(dto.getCpf());

		if (dto.getPassword() != null) {
			this.userService.updatePasswordAndActive(dto.getLogin(), dto.getPassword(), dto.isActive());
		}

		this.technicianRepository.save(existingTechnician);
	}

}
