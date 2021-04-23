package com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class TicketMessage {

    @JsonProperty("id")
    private int id;

    @JsonProperty("price")
    private double price;

    @JsonProperty("bought_on")
    private LocalDateTime boughtOn;

    @JsonProperty("valid_on")
    private LocalDateTime validOn;

    public TicketMessage() {

    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", price=" + price +
                ", boughtOn=" + boughtOn +
                ", validOn=" + validOn +
                '}';
    }
}
