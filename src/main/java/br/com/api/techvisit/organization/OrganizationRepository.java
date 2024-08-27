package br.com.api.techvisit.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.techvisit.organization.model.OrganizationModel;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationModel, Long> {

}
