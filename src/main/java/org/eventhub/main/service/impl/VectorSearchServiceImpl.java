package org.eventhub.main.service.impl;

import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.model.State;
import org.eventhub.main.service.CategoryService;
import org.eventhub.main.service.EventService;
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
    private final EventService eventService;

    @Autowired
    public VectorSearchServiceImpl(EmbeddingClient embeddingClient, JdbcClient jdbcClient, EventService eventService) {
        this.embeddingClient = embeddingClient;
        this.jdbcClient = jdbcClient;
        this.eventService = eventService;
    }

    @Override
    public List<EventResponse> searchEvents(String prompt) {
        List<Double> embedding = embeddingClient.embed(prompt);

        JdbcClient.StatementSpec query = jdbcClient.sql(
                """
SELECT
    events.id,
    event_embeddings.embedding
FROM events
INNER JOIN event_embeddings ON events.embedding_id = event_embeddings.id
WHERE 1 - (embedding <=> :user_prompt::vector) >= 0.7
ORDER BY (embedding <=> :user_prompt::vector) LIMIT 15
""").param("user_prompt", embedding.toString());

        return query.query((resultSet, statementContext) -> {
            long id = resultSet.getLong("id");
            return eventService.readById(id);
        }).list();
    }
}
