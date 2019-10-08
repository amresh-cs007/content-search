package com.interview.webcontent.search.model;

public class WebCrawlerRequest
{
    private String url;
    private int depth;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
