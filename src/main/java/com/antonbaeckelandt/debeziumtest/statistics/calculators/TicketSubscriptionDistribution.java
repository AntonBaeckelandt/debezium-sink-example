package com.antonbaeckelandt.debeziumtest.statistics.calculators;

import com.antonbaeckelandt.debeziumtest.data.RegistrationRepository;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.RegistrationMessage;
import com.antonbaeckelandt.debeziumtest.models.Registration;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TicketSubscriptionDistribution implements Calculator {

    @Autowired
    private RegistrationRepository registrationRepository;

    private int currentPaidByTicket;
    private int currentPaidBySubscription;

    @PostConstruct
    private void init() {
        updateStatistics();
    }

    @Override
    public void onNewCheckin(RegistrationMessage registration) {
        updateStatistics();
    }

    @Override
    public void onCheckOut(RegistrationMessage registration) {
        updateStatistics();
    }

    @Override
    public void appendJson(ObjectNode node) {
        node.put("currentPaidByTicket", currentPaidByTicket);
        node.put("currentPaidBySubscription", currentPaidBySubscription);
    }

    private void updateStatistics() {
        currentPaidBySubscription = 0;
        currentPaidByTicket = 0;
        Iterable<Registration> registrations = registrationRepository.findCurrentlyActiveRegistrations();

        for (Registration registration : registrations) {
            if (registration.getSubscriptionId() != null) {
                ++currentPaidBySubscription;
            } else if (registration.getTicketId() != null) {
                ++currentPaidByTicket;
            }
        }
    }

}
