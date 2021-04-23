package com.antonbaeckelandt.debeziumtest.statistics;

import com.antonbaeckelandt.debeziumtest.kafka.debezium.DatabaseEvent;
import com.antonbaeckelandt.debeziumtest.kafka.debezium.EventType;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.DatabaseEventMessage;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventListeners implements ApplicationListener<DatabaseEventMessage> {

    @Autowired
    private StatisticsCalculator statisticsCalculator;

    private final Logger log = LogManager.getLogger(this.getClass());
    private Map<Class, Consumer<DatabaseEventMessage>> eventHandlers = new HashMap<>();

    public EventListeners() {
        eventHandlers.put(EmployeeMessage.class, this::employeeEvent);
        eventHandlers.put(BraceletMessage.class, this::braceletEvent);
        eventHandlers.put(RegistrationMessage.class, this::registrationEvent);
        eventHandlers.put(TicketMessage.class, this::ticketEvent);
        eventHandlers.put(SubscriptionMessage.class, this::subscriptionEvent);
        eventHandlers.put(CustomerMessage.class, this::customerEvent);
    }

    @Override
    public void onApplicationEvent(DatabaseEventMessage databaseCRUDEvent) {
        Class modelType = databaseCRUDEvent.getDatabaseEvent().getModelType();
        Consumer handler = eventHandlers.get(modelType);
        if (handler != null) {
            handler.accept(databaseCRUDEvent);
        } else {
            log.warn("Unknown event type");
        }
    }

    public void employeeEvent(DatabaseEventMessage<EmployeeMessage> message) {
        log.info("Got employee event");
    }

    public void braceletEvent(DatabaseEventMessage<BraceletMessage> message) {
        log.info("Got bracelet event");
    }

    public void registrationEvent(DatabaseEventMessage<RegistrationMessage> message) {
        log.info("Got registration event");
        DatabaseEvent<RegistrationMessage> event = message.getDatabaseEvent();
        EventType eventType = event.getEventType();
        if (eventType == EventType.INSERT) {
            statisticsCalculator.onNewCheckin(message.getDatabaseEvent().getAfter());
        } else if (eventType == EventType.UPDATE
                && event.getBefore().getCheckOutTimestamp() == null
                && event.getAfter().getCheckOutTimestamp() != null) {
            statisticsCalculator.onCheckOut(message.getDatabaseEvent().getAfter());
        }
    }

    public void ticketEvent(DatabaseEventMessage<TicketMessage> message) {
        log.info("Got Ticket event");
    }

    public void subscriptionEvent(DatabaseEventMessage<SubscriptionMessage> message) {
        log.info("Got subscription event");
    }

    public void customerEvent(DatabaseEventMessage<CustomerMessage> message) {
        log.info("Got customer event");
    }

}
