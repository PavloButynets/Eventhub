package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.mapper.ParticipantMapper;
import org.eventhub.main.model.Participant;
import org.eventhub.main.repository.ParticipantRepository;
import org.eventhub.main.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {


    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository participantRepository, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }


    @Override
    public ParticipantResponse create(ParticipantRequest participantRequest) {
        if(participantRequest != null){
            Participant participant = participantMapper.requestToEntity(participantRequest, new Participant());
            participant = participantRepository.save(participant);
            return participantMapper.entityToResponse(participant);
        }
        throw new NullDtoReferenceException("Request can't be null");
    }

    @Override
    public ParticipantResponse readById(long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Participant with" + id + " id is not found"));
        return participantMapper.entityToResponse(participant);
    }

    @Override
    public Participant readByIdEntity(long id){
        return participantRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Participant with" + id + " id is not found"));
    }
    @Override
    public ParticipantResponse update(ParticipantRequest participantRequest, long id) {
        if(participantRequest != null){
            Participant existingParticipant = readByIdEntity(id);
            Participant participant = participantMapper.requestToEntity(participantRequest, existingParticipant);
            participant = participantRepository.save(participant);
            return participantMapper.entityToResponse(participant);
        }
        throw new NullDtoReferenceException("Request can't be null");
    }

    public void delete(Long id) {
        participantRepository.deleteById(id);
    }

    @Override
    public List<ParticipantResponse> getAll() {
        return participantRepository.findAll()
                .stream()
                .map(participantMapper::entityToResponse)
                .collect(Collectors.toList());
    }

}
