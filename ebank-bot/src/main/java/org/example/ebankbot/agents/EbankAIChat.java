package org.example.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
@Service
public class EbankAIChat {
    private final ChatClient chatClient;

    public EbankAIChat(
            ChatClient.Builder builder,
            ChatMemory chatMemory,
            ToolCallbackProvider tools) {

        this.chatClient = builder
                .defaultSystem("""
                        Vous un assistant qui se charge de répondre aux question de l'utilisateur à 
                        propos des clients et des comptes bancaires en fonction du contexte. 
                        Si aucun contexte n'est fourni, répond avec JE NE SAIS PAS
                        """
                )
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultTools(tools)
                .build();
    }

    public String chat(
             String query,
            @RequestParam(defaultValue = "user-1") String conversationId) {

        return chatClient.prompt()
                .user(query)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
