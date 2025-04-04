package com.example.seoai.service;

import com.example.seoai.model.MetaTagsResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class MetaTagService {

    @Value("${opengraph.api.key}")
    private String apiKey;

    public MetaTagsResult analyze(String url) {
        try {
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }

            // Korrekt encoded URL
            String encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8);
            String urlStr = "https://opengraph.io/api/1.1/site/" + encodedUrl + "?app_id=" + apiKey;
            URI endpoint = new URI(urlStr);  // 🔥 Konverter til URI

            System.out.println("📡 Kald til: " + endpoint);

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(endpoint, Map.class);

            Map<String, Object> hybridGraph = (Map<String, Object>) response.get("hybridGraph");

            MetaTagsResult result = new MetaTagsResult();
            result.setTitle((String) hybridGraph.get("title"));
            result.setDescription((String) hybridGraph.get("description"));

            return result;

        } catch (Exception e) {
            System.out.println("❌ Fejl i meta-analyse: " + e.getMessage());
            MetaTagsResult fallback = new MetaTagsResult();
            fallback.setTitle("Meta-fejl");
            fallback.setDescription("Kunne ikke hente metadata.");
            return fallback;
        }
    }

}
