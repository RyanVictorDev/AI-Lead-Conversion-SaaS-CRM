package com.saascrm.infrastructure.persistence;

import com.saascrm.domain.conversation.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByCompanyId(Long companyId);

    List<Conversation> findByLeadIdAndCompanyId(Long leadId, Long companyId);

    Optional<Conversation> findByIdAndCompanyId(Long id, Long companyId);
}

