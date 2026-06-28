package com.email.smartemail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailGeneratorService {
    private final WebClient webClient;
    private final String apikey;

    public EmailGeneratorService(WebClient.Builder webClientBuilder,
                                 @Value("${gemini.api.url}") String baseUrl,
                                 @Value("${gemini.api.key}") String geminiApiKey)
            {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apikey = geminiApiKey;
    }

    public String generateEmail(EmailRequest emailRequest) {
        String prompt= buildPrompt(emailRequest);

        String requestBody = String.format("""  
                {
                    "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                  }""",prompt);
    }
    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt=new StringBuilder();

        prompt.append("Generate an professional email reply for the following email content:");
        if(emailRequest.getTone() != null && !emailRequest.getTone().isBlank()){
           prompt.append("Use a").append("Tone: "+emailRequest.getTone()).append("Tone.");
        }
        prompt.append("Original Email Content: \n").append(emailRequest.getEmailContent());
        return prompt.toString();
    }
}
