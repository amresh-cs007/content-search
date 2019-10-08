package com.interview.webcontent.search.crawler;

import com.interview.webcontent.search.ingestion.ESIngestion;
import com.interview.webcontent.search.model.WebContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;

@Service
public class WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);
    private static final int MAX_DEPTH = 2;
    private HashSet<String> links;

    @Autowired
    private ESIngestion esIngestion;

    public WebCrawler() {
        links = new HashSet<>();
    }

    public void crawlAndIngestContent(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            logger.info("Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                String content = document.text();
                WebContent webContent = new WebContent();
                webContent.setText(content);
                webContent.setTitle(URL);
                esIngestion.ingest(webContent);

                depth++;
                for (Element page : linksOnPage) {
                    crawlAndIngestContent(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                logger.error("For '" + URL + "': " + e.getMessage());
            }
        }
    }
}
