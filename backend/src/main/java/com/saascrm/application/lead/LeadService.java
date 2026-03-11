package com.saascrm.application.lead;

import com.saascrm.domain.lead.Lead;
import com.saascrm.domain.user.User;
import com.saascrm.infrastructure.persistence.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Transactional
    public LeadResponse createLead(CreateLeadRequest request, User currentUser) {
        Lead lead = new Lead();
        lead.setName(request.name());
        lead.setPhone(request.phone());
        lead.setEmail(request.email());
        lead.setStatus("NEW");
        lead.setCompany(currentUser.getCompany());
        lead.setCreatedAt(OffsetDateTime.now());

        Lead saved = leadRepository.save(lead);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<LeadResponse> listLeads(User currentUser) {
        return leadRepository.findByCompanyId(currentUser.getCompany().getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LeadResponse updateStatus(Long id, UpdateLeadStatusRequest request, User currentUser) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lead not found"));

        if (!lead.getCompany().getId().equals(currentUser.getCompany().getId())) {
            throw new IllegalArgumentException("Lead does not belong to current company");
        }

        lead.setStatus(request.status());
        Lead saved = leadRepository.save(lead);
        return toResponse(saved);
    }

    private LeadResponse toResponse(Lead lead) {
        return new LeadResponse(
                lead.getId(),
                lead.getName(),
                lead.getPhone(),
                lead.getEmail(),
                lead.getStatus()
        );
    }
}

