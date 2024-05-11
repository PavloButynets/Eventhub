package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.config.AuthenticationService;
import org.eventhub.main.config.JwtService;
import org.eventhub.main.dto.CheckboxRequest;
import org.eventhub.main.dto.EventFilterRequest;
import org.eventhub.main.dto.EventResponseXY;
import org.eventhub.main.dto.EventSearchResponse;
import org.eventhub.main.service.FilterService;
import org.eventhub.main.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping
public class FilterController {
    private final FilterService filterService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public FilterController(FilterService filterService, JwtService jwtService, AuthenticationService authenticationService){
        this.filterService = filterService;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/events/filter")
    public ResponseEntity<List<EventSearchResponse>> filter(@RequestBody EventFilterRequest request) {
        log.info("**/get filtered events");
        return new ResponseEntity<>(filterService.filterEvents(request), HttpStatus.OK);
    }

    @PostMapping("/events/checkbox-filter")
    public ResponseEntity<Set<EventSearchResponse>> checkboxFilter(@RequestBody CheckboxRequest request,
                                                                   @RequestHeader("Authorization") String token) {
//        if(jwtService.isExpired(token)){
//            authenticationService.refreshToken(refreshToken);
//        }
        request.setUserId(jwtService.getId(token));
        log.info("**/get filtered events with checkbox");
        return new ResponseEntity<>(filterService.filterCheckboxEvents(request), HttpStatus.OK);
    }

    @GetMapping("/events/all-live-upcoming")
    public ResponseEntity<List<EventResponseXY>> getAllLiveAndUpcoming() {
        log.info("**/get all live and upcoming events ");
        return new ResponseEntity<>(filterService.allLiveAndUpcomingEvents(), HttpStatus.OK);
    }
}
