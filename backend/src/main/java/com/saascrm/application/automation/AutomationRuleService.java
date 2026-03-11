package com.saascrm.application.automation;

import com.saascrm.domain.automation.AutomationRule;
import com.saascrm.domain.user.User;
import com.saascrm.infrastructure.persistence.AutomationRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutomationRuleService {

    private final AutomationRuleRepository ruleRepository;

    public AutomationRuleService(AutomationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Transactional
    public AutomationRuleResponse createRule(AutomationRuleRequest request, User currentUser) {
        AutomationRule rule = new AutomationRule();
        rule.setCompany(currentUser.getCompany());
        rule.setTriggerEvent(request.triggerEvent());
        rule.setCondition(request.condition());
        rule.setAction(request.action());

        AutomationRule saved = ruleRepository.save(rule);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AutomationRuleResponse> listRules(User currentUser) {
        return ruleRepository.findByCompanyId(currentUser.getCompany().getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void deleteRule(Long id, User currentUser) {
        var rule = ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found"));

        if (!rule.getCompany().getId().equals(currentUser.getCompany().getId())) {
            throw new IllegalArgumentException("Rule does not belong to current company");
        }

        ruleRepository.delete(rule);
    }

    private AutomationRuleResponse toResponse(AutomationRule rule) {
        return new AutomationRuleResponse(
                rule.getId(),
                rule.getTriggerEvent(),
                rule.getCondition(),
                rule.getAction()
        );
    }
}

