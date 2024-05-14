package org.eventhub.main.event.applicationEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailUpdateEvent<T> extends ApplicationEvent {
    private final T event;

    public EmailUpdateEvent(T event){
        super(event);
        this.event = event;
    }
}
