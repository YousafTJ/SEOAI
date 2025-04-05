package com.example.seoai.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class LinkCrawlerService {

    public Map<String, List<String>> crawlLinks(String url) {
        List<String> internalLinks = new ArrayList<>();
        List<String> externalLinks = new ArrayList<>();
        List<String> brokenLinks = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/122.0.0.0 Safari/537.36")
                    .timeout(5000)
                    .get();

            Elements links = doc.select("a[href]");
            URL baseUrl = new URL(url);
            String baseDomain = baseUrl.getHost();

            for (Element link : links) {
                String href = link.absUrl("href");
                if (href.isBlank()) continue;

                try {
                    URL linkUrl = new URL(href);
                    String domain = linkUrl.getHost();

                    // Internt vs. eksternt
                    if (domain.equalsIgnoreCase(baseDomain)) {
                        internalLinks.add(href);
                    } else {
                        externalLinks.add(href);
                    }

                    // Opret forbindelse med GET og User-Agent
                    HttpURLConnection conn = (HttpURLConnection) linkUrl.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setConnectTimeout(3000);
                    conn.setReadTimeout(3000);

                    int status = conn.getResponseCode();

                    // Tæl kun virkelige fejl som “døde”
                    if (status >= 400 && status != 403 && status != 999) {
                        brokenLinks.add(href + " (HTTP " + status + ")");
                    }

                } catch (Exception e) {
                    brokenLinks.add(href + " (fejl: " + e.getMessage() + ")");
                }
            }

        } catch (Exception e) {
            brokenLinks.add("❌ Kunne ikke crawle hovedsiden: " + e.getMessage());
        }

        Map<String, List<String>> result = new HashMap<>();
        result.put("internal", internalLinks);
        result.put("external", externalLinks);
        result.put("broken", brokenLinks);
        return result;
    }
}
