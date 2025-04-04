package com.example.seoai.service;

import com.example.seoai.model.PageSpeedResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PageSpeedService {

    @Value("${google.api.key}")
    private String apiKey;

    public PageSpeedResult analyze(String url) {
        String endpoint = "https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url=" + url + "&key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> data = restTemplate.getForObject(endpoint, Map.class);

        Map<String, Object> lighthouse = (Map<String, Object>) data.get("lighthouseResult");
        Map<String, Object> categories = (Map<String, Object>) lighthouse.get("categories");
        Map<String, Object> performance = (Map<String, Object>) categories.get("performance");
        double score = ((Number) performance.get("score")).doubleValue(); // værdi mellem 0.0 og 1.0

        Map<String, Object> audits = (Map<String, Object>) lighthouse.get("audits");

        PageSpeedResult result = new PageSpeedResult();
        result.setPerformanceScore(score * 100); // vi viser det som procent

        result.setFirstContentfulPaint(getDisplayValue(audits, "first-contentful-paint"));
        result.setLargestContentfulPaint(getDisplayValue(audits, "largest-contentful-paint"));
        result.setTimeToInteractive(getDisplayValue(audits, "interactive"));
        result.setSpeedIndex(getDisplayValue(audits, "speed-index"));
        result.setTotalBytes(getDisplayValue(audits, "total-byte-weight"));
        result.setServerResponseTime(getDisplayValue(audits, "server-response-time"));

        return result;
    }

    private String getDisplayValue(Map<String, Object> audits, String key) {
        Map<String, Object> auditEntry = (Map<String, Object>) audits.get(key);
        return (String) auditEntry.get("displayValue");
    }
}
