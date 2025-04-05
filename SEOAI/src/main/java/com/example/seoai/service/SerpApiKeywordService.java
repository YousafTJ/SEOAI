package com.example.seoai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SerpApiKeywordService {

    @Value("${serpapi.api.key}")
    private String apiKey;

    public List<String> getRelatedSearches(String query) {
        try {
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String endpoint = "https://serpapi.com/search.json?q=" + encoded + "&hl=da&gl=dk&api_key=" + apiKey;

            System.out.println("📡 SerpApi-kald: " + endpoint);

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(endpoint, Map.class);

            System.out.println("📦 SerpApi response: " + response);

            List<Map<String, Object>> related = (List<Map<String, Object>>) response.get("related_searches");
            if (related == null) return List.of("Ingen søgeord fundet");

            List<String> keywords = new ArrayList<>();
            for (Map<String, Object> item : related) {
                keywords.add((String) item.get("query"));
            }

            return keywords;

        } catch (Exception e) {
            System.out.println("❌ Fejl i SerpApi: " + e.getMessage());
            return List.of("Kunne ikke hente populære søgninger");
        }
    }





}
