package com.antonbaeckelandt.debeziumtest.statistics.calculators;

import com.antonbaeckelandt.debeziumtest.Util;
import com.antonbaeckelandt.debeziumtest.data.CustomerRepository;
import com.antonbaeckelandt.debeziumtest.data.RegistrationRepository;
import com.antonbaeckelandt.debeziumtest.data.SubscriptionRepository;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.RegistrationMessage;
import com.antonbaeckelandt.debeziumtest.models.Customer;
import com.antonbaeckelandt.debeziumtest.models.Registration;
import com.antonbaeckelandt.debeziumtest.models.Subscription;
import com.antonbaeckelandt.debeziumtest.statistics.Interval;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AgeDistribution implements Calculator {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    private final Map<Interval, Integer> ageDistribution = new HashMap<>();

    public AgeDistribution() {
        ageDistribution.put(new Interval(0, 17), 0);
        ageDistribution.put(new Interval(18, 31), 0);
        ageDistribution.put(new Interval(32, 41), 0);
        ageDistribution.put(new Interval(42, 51), 0);
        ageDistribution.put(new Interval(52, 61), 0);
        ageDistribution.put(new Interval(62, 71), 0);
        ageDistribution.put(new Interval(72, 81), 0);
        ageDistribution.put(new Interval(82, 120), 0);
    }

    @PostConstruct
    private void init() {
        updateStatistics();
    }

    @Override
    public void onNewCheckin(RegistrationMessage registrationMessage) {
        updateStatistics();
    }

    @Override
    public void onCheckOut(RegistrationMessage registration) {
        updateStatistics();
    }

    @Override
    public void appendJson(ObjectNode node) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode ageDistributionJson = mapper.createArrayNode();
        for (Interval interval : ageDistribution.keySet()) {
            ObjectNode intervalNode = mapper.createObjectNode();
            intervalNode.put("start", interval.getStart());
            intervalNode.put("end", interval.getEnd());
            intervalNode.put("count", ageDistribution.get(interval));
            ageDistributionJson.add(intervalNode);
        }
        node.put("ageDistribution", ageDistributionJson);
    }

    private void resetStatistics() {
        ageDistribution.replaceAll((i, v) -> 0);
    }

    private void updateStatistics() {
        resetStatistics();
        Iterable<Registration> registrations = registrationRepository.findCurrentlyActiveRegistrations();

        for (Registration registration : registrations) {
            if (registration.getSubscriptionId() != null) {
                Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(registration.getSubscriptionId());
                if (subscriptionOptional.isPresent()) {
                    Subscription subscription = subscriptionOptional.get();
                    Optional<Customer> customerOptional = customerRepository.findById(subscription.getCustomerId());
                    if (customerOptional.isPresent()) {
                        Customer customer = customerOptional.get();
                        addCustomerToStats(customer);
                    }
                }
            }
        }
    }

    private void addCustomerToStats(Customer customer) {
        LocalDate dateOfBirth = Instant.ofEpochMilli(customer.getDateOfBirth().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int age = Util.calculateAge(dateOfBirth);
        Interval interval = getIntervalForAge(ageDistribution, age);
        if (interval != null) {
            int oldValue = ageDistribution.get(interval);
            ageDistribution.put(interval, ++oldValue);
        }
    }

    private Interval getIntervalForAge(Map<Interval, Integer> dict, int age) {
        for (Interval interval : dict.keySet()) {
            if (interval.includes(age)) {
                return interval;
            }
        }
        return null;
    }

}
