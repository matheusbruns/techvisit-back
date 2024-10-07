package br.com.api.techvisit.visitschedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.techvisit.visitschedule.definition.VisitScheduleModel;

@Repository
public interface VisitScheduleRepository extends JpaRepository<VisitScheduleModel, Long> {

	List<VisitScheduleModel> findAllByOrganizationId(Long organizationId);

}
