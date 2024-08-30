package br.com.api.techvisit.organization;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.techvisit.organization.bean.OrganizationBean;
import br.com.api.techvisit.organization.exception.OrganizationNotFoundException;
import br.com.api.techvisit.organization.factory.OrganizationFactory;
import br.com.api.techvisit.organization.model.OrganizationModel;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	public List<OrganizationBean> getAll() {
		return new OrganizationFactory().build(this.organizationRepository.findAll());
	}

	@Transactional
	public OrganizationBean save(OrganizationBean organizationBean) {
		OrganizationFactory factory = new OrganizationFactory();
		return factory.build(this.organizationRepository.save(factory.build(organizationBean)));
	}

	@Transactional
	public OrganizationBean update(OrganizationBean organizationBean) {
		OrganizationFactory factory = new OrganizationFactory();
		if (this.organizationRepository.existsById(organizationBean.getId())) {
			throw new OrganizationNotFoundException("Organization not found.");
		}

		return factory.build(this.organizationRepository.save(factory.build(organizationBean)));
	}

	@Transactional
	public void delete(List<Long> ids) {
		// TODO: implementar tratativa para quando houver usuários e clientes dessa
		// empresa
		this.organizationRepository.deleteAllById(ids);
	}

	public Optional<OrganizationModel> getOrganization(Long id) {
		return this.organizationRepository.findById(id);
	}

}
