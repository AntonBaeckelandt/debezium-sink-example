package com.antonbaeckelandt.debeziumtest.statistics.calculators;

import com.antonbaeckelandt.debeziumtest.data.RegistrationRepository;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.RegistrationMessage;
import com.antonbaeckelandt.debeziumtest.models.Registration;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class SwimmerCounter implements Calculator {

    @Autowired
    private RegistrationRepository registrationRepository;

    private int currentSwimmersCount = 0;

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
        node.put("currentSwimmersCount", currentSwimmersCount);
    }

    private void updateStatistics() {
        Iterable<Registration> registrations = registrationRepository.findCurrentlyActiveRegistrations();
        List<Registration> registrationList = new ArrayList<>();
        registrations.forEach(registrationList::add);

        currentSwimmersCount = registrationList.size();
    }

}
