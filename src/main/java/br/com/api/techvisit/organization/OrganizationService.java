package br.com.api.techvisit.organization;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.organization.definition.OrganizationDTO;
import br.com.api.techvisit.organization.definition.OrganizationModel;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	public List<OrganizationDTO> getAll() {
		return new OrganizationFactory().build(this.organizationRepository.findAll());
	}

	@Transactional
	public OrganizationDTO save(OrganizationDTO organizationBean) {
		OrganizationFactory factory = new OrganizationFactory();
		return factory.build(this.organizationRepository.save(factory.buildNew(organizationBean)));
	}

	@Transactional
	public OrganizationDTO update(OrganizationDTO organizationDTO) {
		OrganizationFactory factory = new OrganizationFactory();
		if (!this.organizationRepository.existsById(organizationDTO.getId())) {
			throw new OrganizationNotFoundException("Organization not found.");
		}

		return factory.build(this.organizationRepository.save(factory.build(organizationDTO)));
	}

	public Optional<OrganizationModel> getOrganizationById(Long organizationId) {
		return this.organizationRepository.findById(organizationId);
	}

}
