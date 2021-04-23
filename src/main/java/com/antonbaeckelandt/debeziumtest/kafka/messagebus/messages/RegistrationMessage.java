package com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDateTime;

public class RegistrationMessage {

    @JsonProperty("id")
    private int id;

    @JsonProperty("check_in_timestamp")
    private LocalDateTime checkInTimestamp;

    @JsonProperty("check_out_timestamp")
    private LocalDateTime checkOutTimestamp;

    @JsonProperty("bracelet_id")
    private int braceletId;

    @JsonProperty("registered_by_id")
    private int registeredByEmployeeId;

    @JsonProperty("ticket_id")
    @JsonSetter(nulls=Nulls.SKIP)
    private int ticketId = -1;

    @JsonProperty("subscription_id")
    @JsonSetter(nulls=Nulls.SKIP)
    private int subscriptionId = -1;

    @JsonProperty("type")
    private int type;

    public RegistrationMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCheckInTimestamp() {
        return checkInTimestamp;
    }

    public void setCheckInTimestamp(LocalDateTime checkInTimestamp) {
        this.checkInTimestamp = checkInTimestamp;
    }

    public LocalDateTime getCheckOutTimestamp() {
        return checkOutTimestamp;
    }

    public void setCheckOutTimestamp(LocalDateTime checkOutTimestamp) {
        this.checkOutTimestamp = checkOutTimestamp;
    }

    public int getBraceletId() {
        return braceletId;
    }

    public void setBraceletId(int braceletId) {
        this.braceletId = braceletId;
    }

    public int getRegisteredByEmployeeId() {
        return registeredByEmployeeId;
    }

    public void setRegisteredByEmployeeId(int registeredByEmployeeId) {
        this.registeredByEmployeeId = registeredByEmployeeId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", checkInTimestamp=" + checkInTimestamp +
                ", checkOutTimestamp=" + checkOutTimestamp +
                ", braceletId=" + braceletId +
                ", registeredByEmployeeId=" + registeredByEmployeeId +
                ", ticketId=" + ticketId +
                ", subscriptionId=" + subscriptionId +
                ", type=" + type +
                '}';
    }
}
