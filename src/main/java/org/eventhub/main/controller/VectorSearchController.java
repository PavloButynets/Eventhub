package org.eventhub.main.controller;

import groovy.util.logging.Slf4j;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.service.VectorSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class VectorSearchController {

    private final VectorSearchService vectorSearchService;

    @Autowired
    public VectorSearchController(VectorSearchService vectorSearchService) {
        this.vectorSearchService = vectorSearchService;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EventResponse>> search(@RequestParam(name = "prompt") String prompt) {
        List<EventResponse> eventResponse = vectorSearchService.searchEvents(prompt);
        return new ResponseEntity<>(eventResponse, HttpStatus.OK);
    }
}
