package org.eventhub.main.service.impl;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.model.Participant;
import org.eventhub.main.repository.ParticipantRepository;
import org.eventhub.main.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantServiceImpl implements ParticipantService {


    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }


    public void delete(Long id) {
        participantRepository.deleteById(id);
    }

}
