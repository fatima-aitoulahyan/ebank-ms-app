package org.example.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EbankAIChat {
    private final ChatClient chatClient;

    public EbankAIChat(
            ChatClient.Builder builder,
            ChatMemory chatMemory,
            ToolCallbackProvider tools) {

        this.chatClient = builder
                .defaultSystem("""
                        Vous êtes un assistant qui se charge de répondre aux questions de l'utilisateur à 
                        propos des clients et des comptes bancaires en fonction du contexte. 
                        Si aucun contexte n'est fourni, répondez avec JE NE SAIS PAS
                        """
                )
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultTools(tools)
                .build();
    }

    // Méthode principale avec surcharge ou valeur par défaut gérée en Java
    public String chat(Prompt prompt, String conversationId) {
        // Si aucun ID de conversation n'est fourni, on utilise une valeur par défaut
        String activeConversationId = (conversationId != null && !conversationId.isEmpty()) ? conversationId : "user-1";

        return chatClient.prompt(prompt)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID, activeConversationId))
                .call()
                .content();
    }

    // Surcharge de méthode pour garder la possibilité d'appeler avec un seul paramètre
    public String chat(Prompt prompt) {
        return chat(prompt, "user-1");
    }
}