package org.eventhub.main.service.impl;

import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.model.State;
import org.eventhub.main.service.VectorSearchService;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VectorSearchServiceImpl implements VectorSearchService{
    private final EmbeddingClient embeddingClient;
    private final JdbcClient jdbcClient;

    @Autowired
    public VectorSearchServiceImpl(EmbeddingClient embeddingClient, JdbcClient jdbcClient) {
        this.embeddingClient = embeddingClient;
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<EventResponse> searchEvents(String prompt) {
        List<Double> embedding = embeddingClient.embed(prompt);

        JdbcClient.StatementSpec query = jdbcClient.sql(
                """
SELECT
    events.id,
    events.max_participants,
    events.created_at,
    events.start_at,
    events.expire_at,
    events.participant_count,
    events.state,
    events.owner_id,
    events.title,
    events.description,
    events.location,
    event_embeddings.embedding,
    categories.id AS category_id,
    categories.name AS category_name
FROM events
INNER JOIN event_embeddings ON events.embedding_id = event_embeddings.id
LEFT JOIN event_categories ON events.id = event_categories.event_id
LEFT JOIN categories ON event_categories.category_id = categories.id
WHERE 1 - (embedding <=> :user_prompt::vector) >= 0.7
ORDER BY (embedding <=> :user_prompt::vector) LIMIT 15
""").param("user_prompt", embedding.toString());

        Map<Long, EventResponse> eventMap = new HashMap<>();
        return query.query((resultSet, statementContext) -> {
            long eventId = resultSet.getLong("id");

                EventResponse eventResponse = eventMap.computeIfAbsent(eventId, id -> {
                    EventResponse newEvent = new EventResponse();
                    try {
                        newEvent.setId(eventId);
                        newEvent.setTitle(resultSet.getString("title"));
                        newEvent.setMaxParticipants(resultSet.getInt("max_participants"));
                        newEvent.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                        newEvent.setStartAt(resultSet.getObject("start_at", LocalDateTime.class));
                        newEvent.setExpireAt(resultSet.getObject("expire_at", LocalDateTime.class));
                        newEvent.setDescription(resultSet.getString("description"));
                        newEvent.setParticipantCount(resultSet.getInt("participant_count"));
                        newEvent.setState(State.valueOf(resultSet.getString("state")));
                        newEvent.setLocation(resultSet.getString("location"));
                        newEvent.setOwnerId(resultSet.getLong("owner_id"));
                        newEvent.setCategoryResponses(new ArrayList<>());
                    }
                    catch (SQLException ex){
                        throw new RuntimeException(ex.getMessage());
                    }
                    return newEvent;
                });
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setId(resultSet.getLong("category_id"));
                categoryResponse.setName(resultSet.getString("category_name"));
                eventResponse.getCategoryResponses().add(categoryResponse);

                return eventResponse;
        }).list()
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }
    /*
     @Override
    public List<EventResponse> searchEvents(String prompt) {
        List<Double> embedding = embeddingClient.embed(prompt);

        JdbcClient.StatementSpec query = jdbcClient.sql(
                """
SELECT events.id, events.max_participants,events.created_at, events.start_at, events.expire_at,events.participant_count, events.state, events.owner_id, events.title, events.description, events.location, event_embeddings.embedding
FROM events
INNER JOIN event_embeddings ON events.embedding_id=event_embeddings.id
WHERE 1 - (embedding <=> :user_prompt::vector) >= 0.7
ORDER BY (embedding <=> :user_prompt::vector) LIMIT 15
""").param("user_prompt", embedding.toString());


        return query.query(EventResponse.class).list();
    }
    */
}
