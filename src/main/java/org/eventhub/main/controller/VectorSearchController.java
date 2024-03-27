package org.eventhub.main.controller;

import groovy.util.logging.Slf4j;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventSearchResponse;
import org.eventhub.main.service.VectorSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/search")
public class VectorSearchController {

    private final Logger logger = LoggerFactory.getLogger(VectorSearchController.class);

    private final VectorSearchService vectorSearchService;

    @Autowired
    public VectorSearchController(VectorSearchService vectorSearchService) {
        this.vectorSearchService = vectorSearchService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EventSearchResponse>> search(@RequestParam(name = "prompt") String prompt) {
        List<EventSearchResponse> eventsResponse = vectorSearchService.searchEvents(prompt);
        logger.info("**Searching for prompt: " + prompt);
        return new ResponseEntity<>(eventsResponse, HttpStatus.OK);
    }
}
