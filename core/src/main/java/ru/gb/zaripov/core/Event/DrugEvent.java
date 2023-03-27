package ru.gb.zaripov.core.Event;

import org.springframework.context.ApplicationEvent;

public class DrugEvent extends ApplicationEvent {
    private Object sourceObject;
    public final String alertMessage = "someone buys a drug";

    public DrugEvent(Object source) {
        super(source);
    }
}
