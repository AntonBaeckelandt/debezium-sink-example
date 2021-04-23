package com.antonbaeckelandt.debeziumtest;

import com.antonbaeckelandt.debeziumtest.kafka.KafkaTopicConsumers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DebeziumTestApplication {

    @Autowired
    private KafkaTopicConsumers kafkaConsumer;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DebeziumTestApplication.class);
        app.run(args);
    }

}
