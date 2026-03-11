package com.saascrm.application.automation;

public record AutomationRuleResponse(
        Long id,
        String triggerEvent,
        String condition,
        String action
) {
}

