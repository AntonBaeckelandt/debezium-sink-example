package com.antonbaeckelandt.debeziumtest.statistics.messagebus;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.ApplicationEvent;

public class NewStatisticsMessage extends ApplicationEvent {

    private final JsonNode statistics;

    public NewStatisticsMessage(Object source, JsonNode statistics) {
        super(source);
        this.statistics = statistics;
    }

    public JsonNode getStatistics() {
        return statistics;
    }
}
