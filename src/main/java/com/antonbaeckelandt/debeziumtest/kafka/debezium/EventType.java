package com.antonbaeckelandt.debeziumtest.kafka.debezium;

public enum EventType {
    INSERT('c'),
    UPDATE('u'),
    DELETE('d');

    private final char op;

    private EventType(char op) {
        this.op = op;
    }

    public static EventType fromOp(char op) {
        for (EventType e : values()) {
            if (e.op == op) {
                return e;
            }
        }
        return null;
    }

    public char getOp() {
        return op;
    }
}
