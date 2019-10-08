package com.interview.webcontent.search.controller;

import com.interview.webcontent.search.crawler.WebCrawler;
import com.interview.webcontent.search.model.WebContent;
import com.interview.webcontent.search.model.WebCrawlerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webcrawler")
public class WebCrawlerController
{
    @Autowired
    private WebCrawler webCrawler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertWebContent(@RequestBody WebCrawlerRequest request) throws Exception{
        webCrawler.crawlAndIngestContent(request.getUrl(), request.getDepth());
    }
}
