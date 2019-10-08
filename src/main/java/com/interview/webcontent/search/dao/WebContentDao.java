package com.interview.webcontent.search.dao;

import com.interview.webcontent.search.model.WebContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class WebContentDao {

    private static final Logger logger = LoggerFactory.getLogger(WebContentDao.class);
    private final String INDEX = "linkdata";
    private final String TYPE = "links";

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper;

    public WebContentDao(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    public WebContent insertWebContent(WebContent webContent){
        webContent.setId(UUID.randomUUID().toString());
        Map<String, Object> dataMap = objectMapper.convertValue(webContent, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, webContent.getId())
                .source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
        } catch(ElasticsearchException e) {
            logger.error("WebContent " + webContent.toString(), e.getDetailedMessage());
        } catch (java.io.IOException ex){
            logger.error("WebContent " + webContent.toString(), ex.getLocalizedMessage());
        }
        return webContent;
    }

    public Map<String, Object> getWebContentByTerm(String term){
        GetRequest getRequest = new GetRequest(INDEX, TYPE, term);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (java.io.IOException e){
            logger.error("term " + term, e.getLocalizedMessage());
        }
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        return sourceAsMap;
    }
}
