package com.antonbaeckelandt.debeziumtest.statistics.calculators;


import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.RegistrationMessage;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Calculator {

    void onNewCheckin(RegistrationMessage registration);

    void onCheckOut(RegistrationMessage registration);

    void appendJson(ObjectNode node);

}
