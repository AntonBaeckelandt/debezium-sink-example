package com.antonbaeckelandt.debeziumtest.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Ticket {
    private int id;
    private double price;
    private Date boughtOn;
    private Date validOn;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "bought_on")
    public Date getBoughtOn() {
        return boughtOn;
    }

    public void setBoughtOn(Date boughtOn) {
        this.boughtOn = boughtOn;
    }

    @Basic
    @Column(name = "valid_on")
    public Date getValidOn() {
        return validOn;
    }

    public void setValidOn(Date validOn) {
        this.validOn = validOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (Double.compare(ticket.price, price) != 0) return false;
        if (boughtOn != null ? !boughtOn.equals(ticket.boughtOn) : ticket.boughtOn != null) return false;
        if (validOn != null ? !validOn.equals(ticket.validOn) : ticket.validOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (boughtOn != null ? boughtOn.hashCode() : 0);
        result = 31 * result + (validOn != null ? validOn.hashCode() : 0);
        return result;
    }
}
