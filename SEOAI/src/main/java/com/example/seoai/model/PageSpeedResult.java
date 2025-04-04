package com.example.seoai.model;

import lombok.Data;

public class PageSpeedResult {
    private double performanceScore;
    private String firstContentfulPaint;
    private String largestContentfulPaint;
    private String timeToInteractive;
    private String speedIndex;
    private String totalBytes;
    private String serverResponseTime;

    public double getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(double performanceScore) {
        this.performanceScore = performanceScore;
    }

    public String getFirstContentfulPaint() {
        return firstContentfulPaint;
    }

    public void setFirstContentfulPaint(String firstContentfulPaint) {
        this.firstContentfulPaint = firstContentfulPaint;
    }

    public String getLargestContentfulPaint() {
        return largestContentfulPaint;
    }

    public void setLargestContentfulPaint(String largestContentfulPaint) {
        this.largestContentfulPaint = largestContentfulPaint;
    }

    public String getTimeToInteractive() {
        return timeToInteractive;
    }

    public void setTimeToInteractive(String timeToInteractive) {
        this.timeToInteractive = timeToInteractive;
    }

    public String getSpeedIndex() {
        return speedIndex;
    }

    public void setSpeedIndex(String speedIndex) {
        this.speedIndex = speedIndex;
    }

    public String getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(String totalBytes) {
        this.totalBytes = totalBytes;
    }

    public String getServerResponseTime() {
        return serverResponseTime;
    }

    public void setServerResponseTime(String serverResponseTime) {
        this.serverResponseTime = serverResponseTime;
    }
}
