package br.com.api.techvisit.technician;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.techvisit.technician.definition.TechnicianModel;

@Repository
public interface TechnicianRepository extends JpaRepository<TechnicianModel, Long> {

	List<TechnicianModel> findAllByOrganizationId(Long organizationId);

}
