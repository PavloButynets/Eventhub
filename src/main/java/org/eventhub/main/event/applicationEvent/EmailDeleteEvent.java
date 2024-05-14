package org.eventhub.main.event.applicationEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailDeleteEvent<T, V> extends ApplicationEvent {
    private final T event;
    private final V participants;

    public EmailDeleteEvent(T event, V participants){
        super(event);
        this.event = event;
        this.participants = participants;
    }
}
