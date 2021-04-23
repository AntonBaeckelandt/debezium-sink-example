package com.antonbaeckelandt.debeziumtest.kafka;

import com.antonbaeckelandt.debeziumtest.kafka.debezium.DatabaseEvent;
import com.antonbaeckelandt.debeziumtest.kafka.errors.KafkaErrorHandler;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.*;
import com.antonbaeckelandt.debeziumtest.kafka.serialization.DatabaseEventDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    private <T> DefaultKafkaConsumerFactory createConsumerFactory(KafkaProperties properties, Class<T> modelType) {
        DatabaseEventDeserializer<T> databaseEventDeserializer = new DatabaseEventDeserializer<T>(modelType);

        Map<String, Object> props = properties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory(props,
                new StringDeserializer(),
                databaseEventDeserializer);
    }

    private <T> ConcurrentKafkaListenerContainerFactory createFactory(Class<T> modelType) {
        ConcurrentKafkaListenerContainerFactory<String, DatabaseEvent<T>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(createConsumerFactory(kafkaProperties, modelType));
        factory.setErrorHandler(new KafkaErrorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DatabaseEvent<EmployeeMessage>>
    employeeKafkaListenerContainerFactory() {
        return createFactory(EmployeeMessage.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DatabaseEvent<BraceletMessage>>
    braceletKafkaListenerContainerFactory() {
        return createFactory(BraceletMessage.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DatabaseEvent<RegistrationMessage>>
    registrationKafkaListenerContainerFactory() {
        return createFactory(RegistrationMessage.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DatabaseEvent<TicketMessage>>
    ticketKafkaListenerContainerFactory() {
        return createFactory(TicketMessage.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DatabaseEvent<SubscriptionMessage>>
    subscriptionKafkaListenerContainerFactory() {
        return createFactory(SubscriptionMessage.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DatabaseEvent<CustomerMessage>>
    customerKafkaListenerContainerFactory() {
        return createFactory(CustomerMessage.class);
    }

}
