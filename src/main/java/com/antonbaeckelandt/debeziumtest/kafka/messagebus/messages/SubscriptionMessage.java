package com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class SubscriptionMessage {

    @JsonProperty("id")
    private int id;

    @JsonProperty("customer_id")
    private int customerId;

    @JsonProperty("expiration_date")
    private LocalDateTime expirationDate;

    @JsonProperty("bought_on")
    private LocalDateTime boughtOn;

    public SubscriptionMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getBoughtOn() {
        return boughtOn;
    }

    public void setBoughtOn(LocalDateTime boughtOn) {
        this.boughtOn = boughtOn;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", expirationDate=" + expirationDate +
                ", boughtOn=" + boughtOn +
                '}';
    }
}
