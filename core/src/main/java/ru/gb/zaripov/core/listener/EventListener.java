package ru.gb.zaripov.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.core.Event.DrugEvent;

@Service
@Slf4j
public class EventListener {

    @org.springframework.context.event.EventListener(DrugEvent.class)
    public void onDrugEvent(DrugEvent event){
        log.error(event.alertMessage);
    }
}
