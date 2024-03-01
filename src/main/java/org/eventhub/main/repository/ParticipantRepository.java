package org.eventhub.main.repository;

import org.eventhub.main.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}