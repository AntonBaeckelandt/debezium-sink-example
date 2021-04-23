package com.antonbaeckelandt.debeziumtest.kafka.serialization;

import com.antonbaeckelandt.debeziumtest.json.MillisLocalDateTimeDeserializer;
import com.antonbaeckelandt.debeziumtest.kafka.debezium.DatabaseEvent;
import com.antonbaeckelandt.debeziumtest.kafka.debezium.EventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;

public class DatabaseEventDeserializer<T> implements Deserializer<DatabaseEvent<T>> {

    private final Logger log = LogManager.getLogger(this.getClass());
    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> typeParameterClass;

    public DatabaseEventDeserializer(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new MillisLocalDateTimeDeserializer());
        mapper.registerModule(javaTimeModule);
    }

    public DatabaseEvent<T> deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        } else {
            try {
                JsonNode node = this.mapper.readTree(data);
                return this.readEvent(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private DatabaseEvent<T> readEvent(JsonNode node) throws IOException {
        EventType eventType = readEventType(node);
        T before = readModelPayload(node, "before");
        T after = readModelPayload(node, "after");
        return new DatabaseEvent<>(eventType, typeParameterClass, before, after);
    }

    private T readModelPayload(JsonNode node, String key) { // throws IOException {
        JsonNode payload = readKey(node, "payload");
        JsonNode modelNode = payload != null ? readKey(payload, key) : null;
        if (payload != null && modelNode != null) {
            try {
                log.info("Deserializing JSON: " + modelNode);
                return mapper.treeToValue(modelNode, typeParameterClass);
            } catch (JsonProcessingException e) {
                log.error("Error mapping JSON to " + typeParameterClass.getSimpleName() + "\n"
                        + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    private EventType readEventType(JsonNode node) throws IOException {
        JsonNode payload = readKey(node, "payload");
        JsonNode op = (payload == null) ? null : readKey(payload, "op");
        if (payload != null && op != null) {
            char opChar = op.asText().charAt(0);
            return EventType.fromOp(opChar);
        } else {
            return null;
        }
    }

    private JsonNode readKey(JsonNode node, String key) {
        return node.has(key) ? node.get(key) : null;
    }

    public void close() {
    }
}