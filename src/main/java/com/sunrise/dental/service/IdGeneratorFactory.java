package com.sunrise.dental.service;

import java.util.UUID;

public class IdGeneratorFactory {

    public enum IdType {
        BILL, APPOINTMENT
    }

    public static String generate(IdType type) {
        String raw = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        switch (type) {
            case BILL:
                return "B" + raw;
            case APPOINTMENT:
                return "A" + raw;
            default:
                throw new IllegalArgumentException("Unknown ID type: " + type);
        }
    }
}