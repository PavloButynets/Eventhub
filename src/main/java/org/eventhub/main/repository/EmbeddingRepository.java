package org.eventhub.main.repository;

import org.eventhub.main.model.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmbeddingRepository extends JpaRepository<Embedding, Long> {
}
