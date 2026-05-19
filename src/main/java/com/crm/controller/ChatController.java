package com.crm.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.crm.modal.ChatModal;
import com.crm.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.repo.TicketRepo;



@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService service;

    private final SimpMessagingTemplate template;

    public ChatController(ChatService service, SimpMessagingTemplate template) {
        this.service = service;
        this.template = template;
    }

    // 🔥 WebSocket (REAL-TIME + DB SAVE)
    @MessageMapping("/chat.sendMessage/{ticketId}")
    public void send(@Payload ChatModal msg, @DestinationVariable Long ticketId) {

        msg.setCreatedAt(LocalDateTime.now());

        service.saveFromSocket(msg);

        // 🔥 THIS IS REAL BROADCAST
        template.convertAndSend("/topic/chat/" + ticketId, msg);
    }

    // REST fallback (file upload etc.)
    @PostMapping("/upload/{ticketId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','EMPLOYEE')")
    public ResponseEntity<?> addChat(
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable Long ticketId,
            @RequestParam(value = "senderName",required = false) String senderName
    ) throws IOException {

        service.addChat(message, file, ticketId, senderName);

        return ResponseEntity.ok("Chat added successfully");
    }

    @GetMapping("/getChat/{ticketId}")
    public List<ChatModal> getChats(@PathVariable Long ticketId) {
        return service.getChats(ticketId);
    }
}