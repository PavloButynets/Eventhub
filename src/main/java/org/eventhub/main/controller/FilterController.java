package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.EventFilterRequest;
import org.eventhub.main.dto.EventSearchResponse;
import org.eventhub.main.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping
public class FilterController {
    private final FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService){
        this.filterService = filterService;
    }

    @PostMapping("/events/filter")
    public ResponseEntity<List<EventSearchResponse>> filter(@RequestBody EventFilterRequest request) {
        log.info("**/get filtered events");
        return new ResponseEntity<>(filterService.filterEvents(request), HttpStatus.OK);
    }
}
