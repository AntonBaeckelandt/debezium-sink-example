package com.antonbaeckelandt.debeziumtest.statistics.calculators;

import com.antonbaeckelandt.debeziumtest.data.RegistrationRepository;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.RegistrationMessage;
import com.antonbaeckelandt.debeziumtest.models.Registration;
import com.antonbaeckelandt.debeziumtest.statistics.Interval;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SwimmerCountHistory implements Calculator {

    @Autowired
    private RegistrationRepository registrationRepository;

    private final Map<Interval, Integer> swimmerCountHistory = new HashMap<>();

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
        // Do nothing
    }

    @Override
    public void appendJson(ObjectNode node) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode swimmerCounters = mapper.createArrayNode();

        for (Interval interval : swimmerCountHistory.keySet()) {
            ObjectNode counterNode = mapper.createObjectNode();
            counterNode.put("start", interval.getStart());
            counterNode.put("end", interval.getEnd());
            counterNode.put("count", swimmerCountHistory.get(interval));
            swimmerCounters.add(counterNode);
        }
        node.put("swimmerCountHistory", swimmerCounters);
    }

    private Interval getIntervalForTime(LocalDateTime time) {
        return new Interval(time.getHour(), time.getHour() + 1);
    }

    private void resetStatistics() {
        swimmerCountHistory.replaceAll((i, v) -> 0);
    }

    private void updateStatistics() {
        resetStatistics();
        Iterable<Registration> registrations = registrationRepository.findRegistrationSince24h();
        for (Registration registration : registrations) {
            Timestamp checkInTime = registration.getCheckInTimestamp();
            Interval interval = getIntervalForTime(checkInTime.toLocalDateTime());
            addToHistory(interval);
        }
    }

    private void addToHistory(Interval interval) {
        if (swimmerCountHistory.containsKey(interval)) {
            int oldValue = swimmerCountHistory.get(interval);
            swimmerCountHistory.put(interval, ++oldValue);
        } else {
            swimmerCountHistory.put(interval, 1);
        }
    }

}
