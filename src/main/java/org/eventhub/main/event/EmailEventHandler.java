package org.eventhub.main.event;

import org.eventhub.main.dto.EventFullInfoResponse;
import org.eventhub.main.event.applicationEvent.EmailDeleteEvent;
import org.eventhub.main.event.applicationEvent.EmailParticipantApprovalEvent;
import org.eventhub.main.event.applicationEvent.EmailParticipantDeleteEvent;
import org.eventhub.main.event.applicationEvent.EmailUpdateEvent;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.User;
import org.eventhub.main.service.EmailService;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class EmailEventHandler {
    private final EmailService emailService;
    private final UserService userService;
    private final EventService eventService;

    public EmailEventHandler(EmailService emailService,UserService userService, EventService eventService){
        this.emailService = emailService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @EventListener
    @Async
    public void handleParticipantApproval(EmailParticipantApprovalEvent<UUID, UUID> emailParticipantEvent) throws IOException {
        User user = this.userService.readByIdEntity(emailParticipantEvent.getUser());
        Event event = this.eventService.readByIdEntity(emailParticipantEvent.getEvent());

        if(!event.getOwner().getId().equals(user.getId())){
            this.emailService.sendApprovalEmail(user, event.getId(), event.getTitle());
        }
    }

    @EventListener
    @Async
    public void handleParticipantDelete(EmailParticipantDeleteEvent<UUID, UUID> emailParticipantDeleteEvent) throws IOException {
        User user = this.userService.readByIdEntity(emailParticipantDeleteEvent.getUser());
        String title = this.eventService.readByIdEntity(emailParticipantDeleteEvent.getEvent()).getTitle();

        this.emailService.sendExclusionEmail(user, title);
    }

    @EventListener
    @Async
    public void handleEventUpdate(EmailUpdateEvent<EventFullInfoResponse> emailUpdateEvent) throws IOException {
        EventFullInfoResponse event = emailUpdateEvent.getEvent();
        String title = event.getTitle();
        UUID eventId = event.getId();
        List<User> users = this.userService.findApprovedUsersByEventId(eventId);
        if(users != null && !users.isEmpty()){
            this.emailService.sendEmailAboutUpdate(users, eventId, title);
        }
    }

    @EventListener
    @Async
    public void handleEventDelete(EmailDeleteEvent<String, List<User>> emailDeleteEvent) throws IOException {
        List<User> users = emailDeleteEvent.getParticipants();
        if(users != null && !users.isEmpty()){
            this.emailService.sendEventCancellationEmail(users, emailDeleteEvent.getEvent());
        }
    }

}
