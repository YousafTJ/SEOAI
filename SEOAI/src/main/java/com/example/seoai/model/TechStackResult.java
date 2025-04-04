package com.example.seoai.model;

import java.util.List;

public class TechStackResult {
    private List<String> technologies;

    public TechStackResult(List<String> technologies) {
        this.technologies = technologies;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }
}
