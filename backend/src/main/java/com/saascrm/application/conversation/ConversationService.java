package com.saascrm.application.conversation;

import com.saascrm.domain.conversation.Conversation;
import com.saascrm.domain.conversation.Message;
import com.saascrm.domain.user.User;
import com.saascrm.infrastructure.persistence.ConversationRepository;
import com.saascrm.infrastructure.persistence.LeadRepository;
import com.saascrm.infrastructure.persistence.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final LeadRepository leadRepository;
    private final MessageRepository messageRepository;

    public ConversationService(
            ConversationRepository conversationRepository,
            LeadRepository leadRepository,
            MessageRepository messageRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.leadRepository = leadRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public ConversationResponse createConversation(CreateConversationRequest request, User currentUser) {
        var lead = leadRepository.findById(request.leadId())
                .orElseThrow(() -> new IllegalArgumentException("Lead not found"));

        if (!lead.getCompany().getId().equals(currentUser.getCompany().getId())) {
            throw new IllegalArgumentException("Lead does not belong to current company");
        }

        Conversation conversation = new Conversation();
        conversation.setLead(lead);
        conversation.setCompany(currentUser.getCompany());

        var saved = conversationRepository.save(conversation);
        return new ConversationResponse(saved.getId(), saved.getLead().getId());
    }

    @Transactional(readOnly = true)
    public List<ConversationResponse> listConversationsForLead(Long leadId, User currentUser) {
        return conversationRepository.findByLeadIdAndCompanyId(leadId, currentUser.getCompany().getId())
                .stream()
                .map(c -> new ConversationResponse(c.getId(), c.getLead().getId()))
                .toList();
    }

    @Transactional
    public MessageResponse sendMessage(Long conversationId, SendMessageRequest request, User currentUser) {
        var conversation = conversationRepository.findByIdAndCompanyId(
                        conversationId,
                        currentUser.getCompany().getId()
                )
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(request.sender());
        message.setContent(request.content());
        message.setCreatedAt(OffsetDateTime.now());

        var saved = messageRepository.save(message);
        return new MessageResponse(saved.getId(), saved.getSender(), saved.getContent(), saved.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> listMessages(Long conversationId, User currentUser) {
        var conversation = conversationRepository.findByIdAndCompanyId(
                        conversationId,
                        currentUser.getCompany().getId()
                )
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversation.getId())
                .stream()
                .map(m -> new MessageResponse(m.getId(), m.getSender(), m.getContent(), m.getCreatedAt()))
                .toList();
    }
}

