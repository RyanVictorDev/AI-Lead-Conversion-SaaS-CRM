package com.saascrm.presentation.api.conversation;

import com.saascrm.application.conversation.ConversationResponse;
import com.saascrm.application.conversation.ConversationService;
import com.saascrm.application.conversation.CreateConversationRequest;
import com.saascrm.application.conversation.MessageResponse;
import com.saascrm.application.conversation.SendMessageRequest;
import com.saascrm.infrastructure.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final UserRepository userRepository;

    public ConversationController(ConversationService conversationService, UserRepository userRepository) {
        this.conversationService = conversationService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ConversationResponse> createConversation(
            @RequestBody CreateConversationRequest request,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var response = conversationService.createConversation(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConversationResponse>> listConversationsForLead(
            @RequestParam Long leadId,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var conversations = conversationService.listConversationsForLead(leadId, user);
        return ResponseEntity.ok(conversations);
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable Long conversationId,
            @RequestBody SendMessageRequest request,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var response = conversationService.sendMessage(conversationId, request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<MessageResponse>> listMessages(
            @PathVariable Long conversationId,
            Authentication authentication
    ) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        var messages = conversationService.listMessages(conversationId, user);
        return ResponseEntity.ok(messages);
    }
}

