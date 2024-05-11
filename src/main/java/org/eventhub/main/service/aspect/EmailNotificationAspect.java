package org.eventhub.main.service.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.dto.UserResponseBriefInfo;
import org.eventhub.main.model.Event;
import org.eventhub.main.service.EmailService;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class EmailNotificationAspect {
    private final EmailService emailService;
    private final UserService userService;
    private final EventService eventService;
    private final Map<UUID, String> eventTitleMap = new HashMap<>();
    private final Map<UUID,List<UserResponseBriefInfo>> eventParticipantsMap = new HashMap<>();

    @Autowired
    public EmailNotificationAspect(EmailService emailService, UserService userService, EventService eventService){
        this.emailService = emailService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Pointcut(value = "execution(* org.eventhub.main.service.EventService.update(..)) " +
            "&& args(id, eventRequest, token)", argNames = "id,eventRequest,token")
    public void updateEvent(UUID id, EventRequest eventRequest, String token) {}

    @Before(value = "updateEvent(id, eventRequest, token)", argNames = "id,eventRequest,token")
    public void beforeEventUpdate(UUID id, EventRequest eventRequest, String token) {
        Event event = this.eventService.readByIdEntity(id);
        if (event != null) {
            eventTitleMap.put(id, event.getTitle());
        }
    }

    @AfterReturning(pointcut = "updateEvent(id, eventRequest, token)", returning = "response", argNames = "id,eventRequest,token,response")
    public void afterEventUpdate(UUID id, EventRequest eventRequest, String token, Object response) throws IOException {
        List<UserResponseBriefInfo> users = this.userService.findApprovedUsersByEventId(id);
        if (!users.isEmpty()) {
            this.emailService.sendEmailAboutUpdate(users, id, this.eventTitleMap.get(id));
        }
        this.eventTitleMap.remove(id);
    }


    @Pointcut(value = "execution(* org.eventhub.main.service.EventService.delete(..))" +
            "&& args(id, token)", argNames = "id,token")
    public void deleteEvent(UUID id, String token) {}

    @Before(value = "deleteEvent(id, token)", argNames = "id,token")
    public void beforeEventDelete(UUID id, String token) throws IOException {
        this.eventTitleMap.put(id,this.eventService.readByIdEntity(id).getTitle());
        this.eventParticipantsMap.put(id, this.userService.findApprovedUsersByEventId(id));
    }

    @AfterReturning(value = "deleteEvent(id, token)", argNames = "id,token")
    public void afterEventDelete(UUID id, String token) throws IOException {
        List<UserResponseBriefInfo> users = this.eventParticipantsMap.get(id);
        if(!users.isEmpty()){
            this.emailService.sendEventCancellationEmail(users, this.eventTitleMap.get(id));
        }
        eventTitleMap.remove(id);
        eventParticipantsMap.remove(id);
    }

}
