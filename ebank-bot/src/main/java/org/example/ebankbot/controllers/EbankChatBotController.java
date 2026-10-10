package org.example.ebankbot.controllers;

import org.example.ebankbot.agents.EbankAIChat;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatBotController {

    private EbankAIChat ebankAIChat;

    public EbankChatBotController(EbankAIChat ebankAIChat) {

        this.ebankAIChat=ebankAIChat;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam String query,
            @RequestParam(defaultValue = "user-1") String conversationId) {

        return ebankAIChat.chat(new Prompt(query),"user-1");
    }
}
