package com.epam.task2.entity;

public enum CardXmlTag {
    OLD_CARDS("OLD_CARDS"),
    CARD("Card"),

    ID("id"),
    CARD_TYPE("card-type"),
    SEND("send"),

    AUTHORS("Authors"),

    THEM("Them"),
    COUNTRY("Country"),
    DATE("Date"),
    AUTHOR("Author"),
    NAME("name"),
    SER_NAME("ser-name"),
    VALUABLE("Valuable");

    private String value;

    CardXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
