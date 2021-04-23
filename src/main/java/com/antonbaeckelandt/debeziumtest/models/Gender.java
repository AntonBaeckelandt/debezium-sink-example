package com.antonbaeckelandt.debeziumtest.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Gender {
    MALE('M'),
    FEMALE('F');

    private char code;

    Gender(char code) {
        this.code = code;
    }

    @JsonCreator
    public static Gender decode(final char code) {
        return Stream.of(Gender.values()).filter(targetEnum -> targetEnum.code == code).findFirst().orElse(null);
    }

    @JsonValue
    public char getCode() {
        return code;
    }
}
