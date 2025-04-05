package com.example.seoai.service;

import com.example.seoai.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class PdfReportService {

    public byte[] generateSeoPdfWithAi(String url,
                                       PageSpeedResult result,
                                       MetaTagsResult meta,
                                       TechStackResult techStack,
                                       Map<String, List<String>> links,
                                       List<String> keywords,
                                       String aiContent) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("SEO-Analyse med AI", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            document.add(new Paragraph("URL: " + url + "\n\n"));

            document.add(new Paragraph("🔍 Performance", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Score: " + result.getPerformanceScore() + "%"));
            document.add(new Paragraph("First Contentful Paint: " + result.getFirstContentfulPaint()));
            document.add(new Paragraph("Largest Contentful Paint: " + result.getLargestContentfulPaint()));
            document.add(new Paragraph("Speed Index: " + result.getSpeedIndex()));
            document.add(new Paragraph("Time to Interactive: " + result.getTimeToInteractive()));
            document.add(new Paragraph("Total Bytes: " + result.getTotalBytes()));
            document.add(new Paragraph("Server Response Time: " + result.getServerResponseTime()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("📝 Meta-tags", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Titel: " + meta.getTitle()));
            document.add(new Paragraph("Beskrivelse: " + meta.getDescription()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("🧱 Teknologier", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            for (String tech : techStack.getTechnologies()) {
                document.add(new Paragraph("- " + tech));
            }

            document.add(new Paragraph("\n🔗 Links", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Interne Links: " + links.get("internal").size()));
            document.add(new Paragraph("Eksterne Links: " + links.get("external").size()));
            document.add(new Paragraph("Døde Links: " + links.get("broken").size()));

            document.add(new Paragraph("\n📈 Søgeord", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            for (String keyword : keywords) {
                document.add(new Paragraph("- " + keyword));
            }

            document.add(new Paragraph("\n🧠 AI-analyse & anbefalinger", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(aiContent));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
