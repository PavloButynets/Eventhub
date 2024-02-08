package org.eventhub.main.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.mapper.ParticipantMapper;
import org.eventhub.main.model.Participant;
import org.eventhub.main.repository.ParticipantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    public ParticipantResponse create(ParticipantRequest participantRequest) {
        Participant participant = participantMapper.mapToParticipant(participantRequest);
        return participantMapper.mapToParticipantResponse(participantRepository.save(participant));
    }

    public ParticipantResponse getById(Long id) {
        Participant participant = participantRepository.findById(id).orElseThrow();
        return participantMapper.mapToParticipantResponse(participant);
    }

    @Transactional
    public Page<ParticipantResponse> getAll(Predicate filters, Pageable pageable) {
        return participantRepository.findAll((com.querydsl.core.types.Predicate) filters, pageable)
                .map(participantMapper::mapToParticipantResponse);
    }

    public void delete(Long id) {
        participantRepository.deleteById(id);
    }

}
