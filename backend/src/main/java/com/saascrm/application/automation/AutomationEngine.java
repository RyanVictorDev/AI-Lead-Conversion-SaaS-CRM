package com.saascrm.application.automation;

import com.saascrm.domain.automation.AutomationRule;
import com.saascrm.domain.task.Task;
import com.saascrm.infrastructure.persistence.AutomationRuleRepository;
import com.saascrm.infrastructure.persistence.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Motor de automação simplificado que será expandido com eventos reais
 * (lead_created, message_received, task_created, lead_converted).
 */
@Service
public class AutomationEngine {

    private final AutomationRuleRepository ruleRepository;
    private final TaskRepository taskRepository;

    public AutomationEngine(
            AutomationRuleRepository ruleRepository,
            TaskRepository taskRepository
    ) {
        this.ruleRepository = ruleRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Exemplo de execução de automação para o caso:
     * IF lead.status = CONTACTED AND no message for 48h THEN create followup task
     *
     * Aqui ainda não avaliamos a condição em linguagem rica;
     * a ideia é ter um formato estruturado em versões futuras.
     */
    @Transactional
    public void handleLeadNoMessageFor48h(Long companyId, Long leadId) {
        List<AutomationRule> rules = ruleRepository.findByCompanyIdAndTriggerEvent(
                companyId,
                "lead_no_message_48h"
        );

        for (AutomationRule rule : rules) {
            if ("create_followup_task".equalsIgnoreCase(rule.getAction())) {
                Task task = new Task();
                task.setLeadId(leadId);
                task.setTitle("Follow-up after 48h without message");
                task.setDueDate(OffsetDateTime.now().plusDays(1));
                task.setStatus("PENDING");
                taskRepository.save(task);
            }
        }
    }
}

