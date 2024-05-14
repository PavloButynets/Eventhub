package org.eventhub.main.event;

import org.eventhub.main.dto.EventFullInfoResponse;
import org.eventhub.main.event.applicationEvent.EmailDeleteEvent;
import org.eventhub.main.event.applicationEvent.EmailParticipantApprovalEvent;
import org.eventhub.main.event.applicationEvent.EmailParticipantDeleteEvent;
import org.eventhub.main.event.applicationEvent.EmailUpdateEvent;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.Participant;
import org.eventhub.main.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EmailEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EmailEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishParticipantApproval(UUID userId, UUID eventId){
        this.applicationEventPublisher.publishEvent(new EmailParticipantApprovalEvent<>(userId, eventId));
    }

    public void publishParticipantDelete(UUID participantId, UUID eventId){
        this.applicationEventPublisher.publishEvent(new EmailParticipantDeleteEvent<>(participantId, eventId));
    }

    public void publishEventUpdate(EventFullInfoResponse event){
        this.applicationEventPublisher.publishEvent(new EmailUpdateEvent<>(event));
    }

    public void publishEventDelete(String title, List<User> user){
        this.applicationEventPublisher.publishEvent(new EmailDeleteEvent<>(title, user));
    }
}
