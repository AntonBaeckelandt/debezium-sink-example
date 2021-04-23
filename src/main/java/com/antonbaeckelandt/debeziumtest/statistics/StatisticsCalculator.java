package com.antonbaeckelandt.debeziumtest.statistics;

import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.RegistrationMessage;
import com.antonbaeckelandt.debeziumtest.statistics.calculators.Calculator;
import com.antonbaeckelandt.debeziumtest.statistics.calculators.CalculatorList;
import com.antonbaeckelandt.debeziumtest.statistics.messagebus.NewStatisticsMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class StatisticsCalculator {

    @Autowired
    private CalculatorList calculators;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public JsonNode getStatisticsAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        for (Calculator calculator : calculators.getCalculators()) {
            calculator.appendJson(node);
        }
        return node;
    }

    public void onNewCheckin(RegistrationMessage registration) {
        for (Calculator calculator : calculators.getCalculators()) {
            calculator.onNewCheckin(registration);
        }
        applicationEventPublisher.publishEvent(new NewStatisticsMessage(this, getStatisticsAsJson()));
    }

    public void onCheckOut(RegistrationMessage registration) {
        for (Calculator calculator : calculators.getCalculators()) {
            calculator.onCheckOut(registration);
        }
        applicationEventPublisher.publishEvent(new NewStatisticsMessage(this, getStatisticsAsJson()));
    }

}
