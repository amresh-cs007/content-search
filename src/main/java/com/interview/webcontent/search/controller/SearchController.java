package com.interview.webcontent.search.controller;

import com.interview.webcontent.search.model.WebContent;
import com.interview.webcontent.search.dao.WebContentDao;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private WebContentDao webContentDao;

    public SearchController(WebContentDao webContentDao) {
        this.webContentDao = webContentDao;
    }

    @GetMapping("/{term}")
    public Map<String, Object> getWebContentByTerm(@PathVariable String term){
        return webContentDao.getWebContentByTerm(term);
    }
}
