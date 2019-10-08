package com.interview.webcontent.search.ingestion;

import com.interview.webcontent.search.dao.WebContentDao;
import com.interview.webcontent.search.model.WebContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ESIngestion
{
    @Autowired
    private WebContentDao webContentDao;

    public void ingest(WebContent content)
    {
        webContentDao.insertWebContent(content);
    }
}
