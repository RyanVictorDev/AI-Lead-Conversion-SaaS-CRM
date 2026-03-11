package com.saascrm.application.automation;

public record AutomationRuleRequest(
        String triggerEvent,
        String condition,
        String action
) {
}

