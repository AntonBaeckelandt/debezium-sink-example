package com.antonbaeckelandt.debeziumtest.kafka.debezium;

public class DatabaseEvent<T> {

    private final EventType eventType;

    private final T before;
    private final T after;

    private final Class<T> modelType;

    public DatabaseEvent(EventType eventType, Class<T> modelType) {
        this(eventType, modelType, null, null);
    }

    public DatabaseEvent(EventType eventType, Class<T> modelType, T before) {
        this(eventType, modelType, before, null);
    }

    public DatabaseEvent(EventType eventType, Class<T> modelType, T before, T after) {
        this.eventType = eventType;
        this.modelType = modelType;
        this.before = before;
        this.after = after;
    }

    public EventType getEventType() {
        return eventType;
    }

    public T getBefore() {
        return before;
    }

    public T getAfter() {
        return after;
    }

    public Class<T> getModelType() {
        return modelType;
    }
}
