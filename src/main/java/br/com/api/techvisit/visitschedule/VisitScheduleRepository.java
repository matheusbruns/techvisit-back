package br.com.api.techvisit.visitschedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.api.techvisit.visitschedule.definition.VisitScheduleModel;

@Repository
public interface VisitScheduleRepository extends JpaRepository<VisitScheduleModel, Long> {

	@Query("SELECT vs FROM VisitScheduleModel vs WHERE vs.organization.id = :organizationId ORDER BY vs.startDate DESC")
	List<VisitScheduleModel> findAllByOrganizationId(Long organizationId);

}
