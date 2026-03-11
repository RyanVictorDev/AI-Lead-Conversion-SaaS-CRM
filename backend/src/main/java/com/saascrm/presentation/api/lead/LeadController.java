package com.saascrm.presentation.api.lead;

import com.saascrm.application.lead.CreateLeadRequest;
import com.saascrm.application.lead.LeadResponse;
import com.saascrm.application.lead.LeadService;
import com.saascrm.application.lead.UpdateLeadStatusRequest;
import com.saascrm.infrastructure.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;
    private final UserRepository userRepository;

    public LeadController(LeadService leadService, UserRepository userRepository) {
        this.leadService = leadService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<LeadResponse> createLead(
            @RequestBody CreateLeadRequest request,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var response = leadService.createLead(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LeadResponse>> listLeads(Authentication authentication) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var leads = leadService.listLeads(user);
        return ResponseEntity.ok(leads);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<LeadResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateLeadStatusRequest request,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var response = leadService.updateStatus(id, request, user);
        return ResponseEntity.ok(response);
    }
}

