package com.saascrm.presentation.api.automation;

import com.saascrm.application.automation.AutomationRuleRequest;
import com.saascrm.application.automation.AutomationRuleResponse;
import com.saascrm.application.automation.AutomationRuleService;
import com.saascrm.infrastructure.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation/rules")
public class AutomationRuleController {

    private final AutomationRuleService automationRuleService;
    private final UserRepository userRepository;

    public AutomationRuleController(
            AutomationRuleService automationRuleService,
            UserRepository userRepository
    ) {
        this.automationRuleService = automationRuleService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<AutomationRuleResponse> createRule(
            @RequestBody AutomationRuleRequest request,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var response = automationRuleService.createRule(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AutomationRuleResponse>> listRules(Authentication authentication) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var rules = automationRuleService.listRules(user);
        return ResponseEntity.ok(rules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(
            @PathVariable Long id,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        automationRuleService.deleteRule(id, user);
        return ResponseEntity.noContent().build();
    }
}

