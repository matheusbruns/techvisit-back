package br.com.api.techvisit.visitschedule;

import br.com.api.techvisit.visitschedule.definition.VisitStatus;

public record EditStatusRequestDTO(Long visitScheduleId, VisitStatus status) {

}
