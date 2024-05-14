package org.eventhub.main.event.applicationEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailParticipantApprovalEvent<T, V> extends ApplicationEvent {
    private final T user;
    private final V event;

    public EmailParticipantApprovalEvent(T user, V event){
        super(user);
        this.user = user;
        this.event = event;
    }
}
