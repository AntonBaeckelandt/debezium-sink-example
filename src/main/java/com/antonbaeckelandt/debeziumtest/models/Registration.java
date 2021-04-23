package com.antonbaeckelandt.debeziumtest.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Registration {
    private int id;
    private Timestamp checkInTimestamp;
    private Timestamp checkOutTimestamp;
    private int type;
    private int braceletId;
    private int registeredById;
    private Integer ticketId;
    private Integer subscriptionId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "check_in_timestamp")
    public Timestamp getCheckInTimestamp() {
        return checkInTimestamp;
    }

    public void setCheckInTimestamp(Timestamp checkInTimestamp) {
        this.checkInTimestamp = checkInTimestamp;
    }

    @Basic
    @Column(name = "check_out_timestamp")
    public Timestamp getCheckOutTimestamp() {
        return checkOutTimestamp;
    }

    public void setCheckOutTimestamp(Timestamp checkOutTimestamp) {
        this.checkOutTimestamp = checkOutTimestamp;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Registration that = (Registration) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (checkInTimestamp != null ? !checkInTimestamp.equals(that.checkInTimestamp) : that.checkInTimestamp != null)
            return false;
        if (checkOutTimestamp != null ? !checkOutTimestamp.equals(that.checkOutTimestamp) : that.checkOutTimestamp != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (checkInTimestamp != null ? checkInTimestamp.hashCode() : 0);
        result = 31 * result + (checkOutTimestamp != null ? checkOutTimestamp.hashCode() : 0);
        result = 31 * result + type;
        return result;
    }

    @Basic
    @Column(name = "bracelet_id")
    public int getBraceletId() {
        return braceletId;
    }

    public void setBraceletId(int braceletId) {
        this.braceletId = braceletId;
    }

    @Basic
    @Column(name = "registered_by_id")
    public int getRegisteredById() {
        return registeredById;
    }

    public void setRegisteredById(int registeredById) {
        this.registeredById = registeredById;
    }

    @Basic
    @Column(name = "ticket_id")
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    @Column(name = "subscription_id")
    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
