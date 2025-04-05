package com.example.seoai.service;

import com.example.seoai.model.TechStackResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JsoupTechAnalyzerService {

    public TechStackResult analyze(String url) {
        List<String> technologies = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // 🔍 1. META-generator (CMS)
            Element generatorMeta = doc.selectFirst("meta[name=generator]");
            if (generatorMeta != null) {
                String generator = generatorMeta.attr("content");
                technologies.add("CMS: " + generator);
            }

            // 🧠 2. script[src] - JavaScript libraries / analytics
            for (Element script : doc.select("script[src]")) {
                String src = script.attr("src").toLowerCase();

                if (src.contains("gtag/js") || src.contains("analytics.js")) {
                    technologies.add("Google Analytics");
                }
                if (src.contains("googletagmanager.com")) {
                    technologies.add("Google Tag Manager");
                }
                if (src.contains("hotjar.com")) {
                    technologies.add("Hotjar");
                }
                if (src.contains("facebook.net")) {
                    technologies.add("Facebook Pixel");
                }
                if (src.contains("jquery")) {
                    technologies.add("jQuery");
                }
                if (src.contains("react")) {
                    technologies.add("React.js");
                }
                if (src.contains("vue")) {
                    technologies.add("Vue.js");
                }
                if (src.contains("next")) {
                    technologies.add("Next.js");
                }
            }

            // 🎨 3. link[href] - CSS frameworks
            for (Element link : doc.select("link[href]")) {
                String href = link.attr("href").toLowerCase();

                if (href.contains("bootstrap")) {
                    technologies.add("Bootstrap");
                }
                if (href.contains("tailwind")) {
                    technologies.add("Tailwind CSS");
                }
                if (href.contains("fontawesome")) {
                    technologies.add("Font Awesome");
                }
            }

            // 🧹 Fjern dubletter
            Set<String> unique = new LinkedHashSet<>(technologies);
            if (unique.isEmpty()) {
                unique.add("Ingen teknologier fundet");
            }

            return new TechStackResult(new ArrayList<>(unique));

        } catch (Exception e) {
            System.out.println("❌ JSoup-analysefejl: " + e.getMessage());
            return new TechStackResult(List.of("Fejl under teknologi-analyse"));
        }
    }
}
