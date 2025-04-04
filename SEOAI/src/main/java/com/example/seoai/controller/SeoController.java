package com.example.seoai.controller;

import com.example.seoai.model.MetaTagsResult;
import com.example.seoai.model.PageSpeedResult;
import com.example.seoai.service.MetaTagService;
import com.example.seoai.service.PageSpeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SeoController {

    @Autowired
    private PageSpeedService pageSpeedService;

    @Autowired
    private MetaTagService metaTagService;





    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam String url, Model model) {
        // 🛠 Sikrer korrekt URL-format
        if (url.startsWith("https:/") && !url.startsWith("https://")) {
            url = url.replace("https:/", "https://");
        }
        if (url.startsWith("http:/") && !url.startsWith("http://")) {
            url = url.replace("http:/", "http://");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        // ✅ Vis debug
        System.out.println("🌐 Final cleaned URL: " + url);

        // Kald services
        PageSpeedResult performance = pageSpeedService.analyze(url);
        MetaTagsResult meta = metaTagService.analyze(url);
        // Send til view
        model.addAttribute("url", url);
        model.addAttribute("result", performance);
        model.addAttribute("meta", meta);

        return "result";
    }
}
