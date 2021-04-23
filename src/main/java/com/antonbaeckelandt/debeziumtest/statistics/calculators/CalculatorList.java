package com.antonbaeckelandt.debeziumtest.statistics.calculators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class CalculatorList {

    @Autowired
    private AgeDistribution ageDistribution;

    @Autowired
    private GenderDistribution genderDistribution;

    @Autowired
    private SwimmerCounter swimmerCounter;

    @Autowired
    private SwimmerCountHistory swimmerCountHistory;

    @Autowired
    private TicketSubscriptionDistribution ticketSubscriptionDistribution;


    private final List<Calculator> calculators = new LinkedList<>();

    @PostConstruct
    private void init() {
        calculators.add(ageDistribution);
        calculators.add(genderDistribution);
        calculators.add(swimmerCounter);
        calculators.add(swimmerCountHistory);
        calculators.add(ticketSubscriptionDistribution);
    }

    public List<Calculator> getCalculators() {
        return Collections.unmodifiableList(calculators);
    }

}
