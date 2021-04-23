package com.antonbaeckelandt.debeziumtest.statistics.calculators;

import com.antonbaeckelandt.debeziumtest.data.CustomerRepository;
import com.antonbaeckelandt.debeziumtest.data.RegistrationRepository;
import com.antonbaeckelandt.debeziumtest.data.SubscriptionRepository;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.RegistrationMessage;
import com.antonbaeckelandt.debeziumtest.models.Customer;
import com.antonbaeckelandt.debeziumtest.models.Gender;
import com.antonbaeckelandt.debeziumtest.models.Registration;
import com.antonbaeckelandt.debeziumtest.models.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GenderDistribution implements Calculator {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    private final Map<Gender, Integer> genderDistribution = new HashMap<>();

    public GenderDistribution() {
        genderDistribution.put(Gender.MALE, 0);
        genderDistribution.put(Gender.FEMALE, 0);
    }

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

    private void resetStatistics() {
        genderDistribution.replaceAll((i, v) -> 0);
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
        Gender gender = Gender.decode(customer.getGender().charAt(0));
        int oldValue = genderDistribution.get(gender);
        genderDistribution.put(gender, ++oldValue);
    }

    private Customer getCustomerFromRegistration(RegistrationMessage registration) {
        int subscriptionId = registration.getSubscriptionId();
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(subscriptionId);
        if (subscriptionOptional.isEmpty()) {
            return null;
        }

        Subscription subscription = subscriptionOptional.get();
        int customerId = subscription.getCustomerId();
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            return null;
        }
        return customerOptional.get();
    }

    @Override
    public void appendJson(ObjectNode node) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode genderDistributionJson = mapper.createArrayNode();
        for (Gender gender : genderDistribution.keySet()) {
            ObjectNode genderNode = mapper.createObjectNode();
            genderNode.put("gender", String.valueOf(gender.getCode()));
            genderNode.put("count", genderDistribution.get(gender));
            genderDistributionJson.add(genderNode);
        }
        node.put("genderDistribution", genderDistributionJson);
    }

}
