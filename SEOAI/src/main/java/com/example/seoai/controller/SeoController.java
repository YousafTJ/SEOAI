package com.example.seoai.controller;

import com.example.seoai.model.MetaTagsResult;
import com.example.seoai.model.PageSpeedResult;
import com.example.seoai.model.TechStackResult;
import com.example.seoai.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class SeoController {

    @Autowired
    private PageSpeedService pageSpeedService;

    @Autowired
    private MetaTagService metaTagService;

    @Autowired
    JsoupTechAnalyzerService jsoupTechAnalyzerService;

    @Autowired
    LinkCrawlerService linkCrawlerService;

    @Autowired
    SerpApiKeywordService serpApiKeywordService;

    @Autowired
    PdfReportService pdfReportService;

    @Autowired
    DeepSeekAiService deepSeekAiService;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam String url,
                          @RequestParam String industry,
                          Model model,
                          HttpSession session) {
        // 💡 Rens URL-format
        if (url.startsWith("https:/") && !url.startsWith("https://")) {
            url = url.replace("https:/", "https://");
        }
        if (url.startsWith("http:/") && !url.startsWith("http://")) {
            url = url.replace("http:/", "http://");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        // 📦 Gem branche i session (til PDF senere)
        session.setAttribute("industry", industry);

        // 🔍 Kør SEO-analyse
        PageSpeedResult performance = pageSpeedService.analyze(url);
        MetaTagsResult meta = metaTagService.analyze(url);
        TechStackResult techStack = jsoupTechAnalyzerService.analyze(url);
        Map<String, List<String>> links = linkCrawlerService.crawlLinks(url);
        List<String> keywords = serpApiKeywordService.getRelatedSearches(industry);

        // 📤 Send til view
        model.addAttribute("url", url);
        model.addAttribute("result", performance);
        model.addAttribute("meta", meta);
        model.addAttribute("technologies", techStack.getTechnologies());
        model.addAttribute("internalLinks", links.get("internal"));
        model.addAttribute("externalLinks", links.get("external"));
        model.addAttribute("brokenLinks", links.get("broken"));
        model.addAttribute("keywords", keywords);

        return "result";
    }


    @GetMapping("/download-report")
    public ResponseEntity<byte[]> downloadReport(@RequestParam String url, HttpSession session) {
        try {
            String industry = (String) session.getAttribute("industry");
            if (industry == null || industry.isBlank()) industry = "generel";

            PageSpeedResult result = pageSpeedService.analyze(url);
            MetaTagsResult meta = metaTagService.analyze(url);
            TechStackResult techStack = jsoupTechAnalyzerService.analyze(url);
            Map<String, List<String>> links = linkCrawlerService.crawlLinks(url);
            List<String> keywords = serpApiKeywordService.getRelatedSearches(industry);

            String aiAnalysis = deepSeekAiService.analyzeSeoAndSuggest(result, meta, techStack, links, keywords);
            byte[] pdf = pdfReportService.generateSeoPdfWithAi(url, result, meta, techStack, links, keywords, aiAnalysis);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=seo-ai-rapport.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            System.out.println("❌ PDF-download fejlede: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


}
