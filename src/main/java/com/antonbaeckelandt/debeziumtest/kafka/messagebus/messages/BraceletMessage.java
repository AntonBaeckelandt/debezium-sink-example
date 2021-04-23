package com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BraceletMessage {

    @JsonProperty("id")
    private int id;

    public BraceletMessage() {

    }

    public BraceletMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
