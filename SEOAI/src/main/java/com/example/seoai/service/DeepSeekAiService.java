package com.example.seoai.service;

import com.example.seoai.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@Service
public class DeepSeekAiService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    private final String endpoint = "https://api.deepseek.com/v1/chat/completions";

    public String analyzeSeoAndSuggest(PageSpeedResult result,
                                       MetaTagsResult meta,
                                       TechStackResult tech,
                                       Map<String, List<String>> links,
                                       List<String> keywords) {

        String prompt = buildPrompt(result, meta, tech, links, keywords);

        JSONObject payload = new JSONObject()
                .put("model", "deepseek-chat")
                .put("temperature", 0.7)
                .put("messages", List.of(
                        new JSONObject().put("role", "system").put("content", "Du er en SEO-ekspert, der analyserer tekniske SEO-data og foreslår forbedringer."),
                        new JSONObject().put("role", "user").put("content", prompt)
                ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<String> request = new HttpEntity<>(payload.toString(), headers);

        try {
            RestTemplate rest = new RestTemplate();
            ResponseEntity<String> response = rest.exchange(endpoint, HttpMethod.POST, request, String.class);
            JSONObject json = new JSONObject(response.getBody());
            return json.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (Exception e) {
            e.printStackTrace();
            return "Kunne ikke hente AI-analyse fra DeepSeek.";
        }
    }

    private String buildPrompt(PageSpeedResult result, MetaTagsResult meta, TechStackResult tech,
                               Map<String, List<String>> links, List<String> keywords) {
        return """
                Her er SEO-data fra en hjemmeside:

                🔍 Performance Score: %.0f%%
                - First Contentful Paint: %s
                - Largest Contentful Paint: %s
                - Speed Index: %s
                - Time to Interactive: %s
                - Total Bytes: %s
                - Server Response Time: %s

                📝 Meta Title: %s
                📝 Meta Description: %s

                🧱 Teknologier: %s

                🔗 Interne Links: %d, Eksterne Links: %d, Døde Links: %d

                📈 Søgeord: %s

                Kom med konkrete forslag til forbedringer af siden. Inkludér både tekniske og indholdsmæssige anbefalinger.
                """.formatted(
                result.getPerformanceScore(),
                result.getFirstContentfulPaint(),
                result.getLargestContentfulPaint(),
                result.getSpeedIndex(),
                result.getTimeToInteractive(),
                result.getTotalBytes(),
                result.getServerResponseTime(),
                meta.getTitle(),
                meta.getDescription(),
                String.join(", ", tech.getTechnologies()),
                links.get("internal").size(),
                links.get("external").size(),
                links.get("broken").size(),
                String.join(", ", keywords)
        );
    }
}
