package com.example.seoai.service;

import com.example.seoai.model.TechStackResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class JsoupTechAnalyzerService {

    public TechStackResult analyze(String url) {
        List<String> technologies = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // 📦 1. CMS
            Element generatorMeta = doc.selectFirst("meta[name=generator]");
            if (generatorMeta != null) {
                String generator = generatorMeta.attr("content");
                technologies.add("CMS: " + generator);
            }

            // 📊 2. Analytics (GA, GTM)
            for (Element script : doc.select("script[src]")) {
                String src = script.attr("src");

                if (src.contains("gtag/js")) {
                    technologies.add("Google Analytics");
                } else if (src.contains("googletagmanager.com")) {
                    technologies.add("Google Tag Manager");
                } else if (src.contains("hotjar.com")) {
                    technologies.add("Hotjar");
                } else if (src.contains("facebook.net")) {
                    technologies.add("Facebook Pixel");
                }
            }

            // 💄 3. Frameworks (CSS/js)
            for (Element link : doc.select("link[href]")) {
                String href = link.attr("href");

                if (href.contains("bootstrap")) {
                    technologies.add("Bootstrap");
                } else if (href.contains("fontawesome")) {
                    technologies.add("FontAwesome");
                }
            }

            // 📜 jQuery
            if (doc.toString().contains("jquery")) {
                technologies.add("jQuery");
            }

            if (technologies.isEmpty()) {
                technologies.add("Ingen teknologier identificeret");
            }

            return new TechStackResult(technologies);

        } catch (Exception e) {
            System.out.println("❌ Fejl i JSoup-analyse: " + e.getMessage());
            return new TechStackResult(List.of("Fejl under analyse"));
        }
    }
}
