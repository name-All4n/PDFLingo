package br.com.Myproject.PDFLingo.service;

import br.com.Myproject.PDFLingo.model.TextRequest;
import br.com.Myproject.PDFLingo.translationClasses.ResponseDTO;
import br.com.Myproject.PDFLingo.translationClasses.TranslationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AIExerciseService {
    private final WebClient webClient;

    @Value("${groq.api.key}")
    private String apiKey;

    public AIExerciseService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.groq.com").build();
    }

    public TranslationResponse translating(TextRequest request) {

        String prompt = """
                traduza o texto a seguir %s.
                devolva apenas a tradução
                sem explicações adicionais.
                """.formatted(
                request.phrase()

        );

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        ResponseDTO response =  webClient.post()
                .uri("/openai/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();

        String text = response.choices().get(0).message().content();
        return new TranslationResponse(text);
    }

}
