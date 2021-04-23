package com.antonbaeckelandt.debeziumtest.kafka;

import com.antonbaeckelandt.debeziumtest.kafka.debezium.DatabaseEvent;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.DatabaseEventMessage;
import com.antonbaeckelandt.debeziumtest.kafka.messagebus.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaTopicConsumers {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(id = "1", topics = "mysql_local_server.swimming_pool.employee", containerFactory = "employeeKafkaListenerContainerFactory")
    public void listenEmployee(@Payload(required = false) DatabaseEvent<EmployeeMessage> dbEvent, Acknowledgment acknowledgment) {
        publishEvent(dbEvent, acknowledgment);
    }

    @KafkaListener(id = "2", topics = "mysql_local_server.swimming_pool.admission_bracelet", containerFactory = "braceletKafkaListenerContainerFactory")
    public void listenBracelet(@Payload(required = false) DatabaseEvent<BraceletMessage> dbEvent, Acknowledgment acknowledgment) {
        publishEvent(dbEvent, acknowledgment);
    }

    @KafkaListener(id = "3", topics = "mysql_local_server.swimming_pool.registration", containerFactory = "registrationKafkaListenerContainerFactory")
    public void listenRegistration(@Payload(required = false) DatabaseEvent<RegistrationMessage> dbEvent, Acknowledgment acknowledgment) {
        publishEvent(dbEvent, acknowledgment);
    }

    @KafkaListener(id = "4", topics = "mysql_local_server.swimming_pool.ticket", containerFactory = "ticketKafkaListenerContainerFactory")
    public void listenTicket(@Payload(required = false) DatabaseEvent<TicketMessage> dbEvent, Acknowledgment acknowledgment) {
        publishEvent(dbEvent, acknowledgment);
    }

    @KafkaListener(id = "5", topics = "mysql_local_server.swimming_pool.subscription", containerFactory = "subscriptionKafkaListenerContainerFactory")
    public void listenSubscription(@Payload(required = false) DatabaseEvent<SubscriptionMessage> dbEvent, Acknowledgment acknowledgment) {
        publishEvent(dbEvent, acknowledgment);
    }

    @KafkaListener(id = "6", topics = "mysql_local_server.swimming_pool.customer", containerFactory = "customerKafkaListenerContainerFactory")
    public void listenCustomer(@Payload(required = false) DatabaseEvent<CustomerMessage> dbEvent, Acknowledgment acknowledgment) {
        publishEvent(dbEvent, acknowledgment);
    }

    private void publishEvent(DatabaseEvent dbEvent, Acknowledgment acknowledgment) {
        if (dbEvent != null) {
            applicationEventPublisher.publishEvent(new DatabaseEventMessage<>(this, dbEvent));
        }
        acknowledgment.acknowledge();
    }

}