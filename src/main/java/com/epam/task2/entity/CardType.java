package com.epam.task2.entity;

public enum CardType {
    ADVERTISING("advertising"),
    CONGRATULATORY("congratulatory");

    private String value;

    CardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
