package org.eventhub.main.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.mapper.EventMapper;
import org.eventhub.main.model.Category;
import org.eventhub.main.model.Event;
import org.eventhub.main.repository.FilterRepository;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.FilterService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FilterServiceImpl implements FilterService {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final FilterRepository filterRepository;
    public FilterServiceImpl(EventService eventService, EventMapper eventMapper, FilterRepository filterRepository){
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.filterRepository = filterRepository;
    }
    @Override
    public List<EventSearchResponse> filterEvents(EventFilterRequest filterRequest) {

        Stream<Event> stream = this.eventService.getAllEntities().stream();
        if (!filterRequest.getLocation().isBlank()) {
            stream = stream.filter(event -> event.getLocation().equals(filterRequest.getLocation()));
        }
        if (filterRequest.getMinParticipants() > 2) {
            stream = stream.filter(event -> event.getMaxParticipants() >= filterRequest.getMinParticipants());
        }
        if (filterRequest.getMaxParticipants() > 2) {
            stream = stream.filter(event -> event.getMaxParticipants() <= filterRequest.getMaxParticipants());
        }
        if (filterRequest.getStartAt() != null) {
            stream = stream.filter(event -> !event.getStartAt().isBefore(filterRequest.getStartAt()));
        }
        if (filterRequest.getExpireAt() != null) {
            stream = stream.filter(event -> !event.getExpireAt().isAfter(filterRequest.getExpireAt()));
        }
        if (!filterRequest.getCategoryRequests().isEmpty()) {
            Set<String> filterCategories = filterRequest.getCategoryRequests()
                    .stream()
                    .map(CategoryRequest::getName)
                    .collect(Collectors.toSet());

            stream = stream.filter(event -> {
                Set<String> eventCategories = event.getCategories()
                        .stream()
                        .map(Category::getName)
                        .collect(Collectors.toSet());
                return eventCategories.containsAll(filterCategories);
            });
        }
        return stream.map(eventMapper::entityToSearchResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Set<EventSearchResponse> filterCheckboxEvents(CheckboxRequest checkboxRequest) {

        Set<Event> results = new HashSet<>();

        log.info(String.valueOf(checkboxRequest));
        if (checkboxRequest.isMyEvents()) {
            results.addAll(filterRepository.findMyEvents(checkboxRequest.getUserId()));
        }

        if (checkboxRequest.isJoinedEvents()) {
            results.addAll(filterRepository.findJoinedEvents(checkboxRequest.getUserId()));
        }

        if (checkboxRequest.isPendingEvents()) {
            results.addAll(filterRepository.findPendingEvents(checkboxRequest.getUserId()));
        }

        if (checkboxRequest.isArchiveEvents()) {
            results.addAll(filterRepository.findArchiveEvents(checkboxRequest.getUserId(), LocalDateTime.now()));
        }

        return results.stream().map(eventMapper::entityToSearchResponse)
                .collect(Collectors.toSet());
    }
}
