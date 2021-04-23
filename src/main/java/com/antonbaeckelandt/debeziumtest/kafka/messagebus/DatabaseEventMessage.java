package com.antonbaeckelandt.debeziumtest.kafka.messagebus;

import com.antonbaeckelandt.debeziumtest.kafka.debezium.DatabaseEvent;
import org.springframework.context.ApplicationEvent;

public class DatabaseEventMessage<T> extends ApplicationEvent {

    private final DatabaseEvent<T> databaseEvent;


    public DatabaseEventMessage(Object source, DatabaseEvent<T> databaseEvent) {
        super(source);
        this.databaseEvent = databaseEvent;
    }

    public DatabaseEvent<T> getDatabaseEvent() {
        return databaseEvent;
    }

}
