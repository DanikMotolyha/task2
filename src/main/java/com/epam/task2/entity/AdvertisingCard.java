package com.epam.task2.entity;

import java.time.LocalDate;
import java.util.List;

public class AdvertisingCard extends Card {
    private CardType type = CardType.ADVERTISING;

    public AdvertisingCard(int id, String them, boolean send, String country, LocalDate date, List<Author> authors, String valuable) {
        super(id, them, send, country, date, authors, valuable);
    }

    public AdvertisingCard() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisingCard)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AdvertisingCard that = (AdvertisingCard) o;
        return type == that.type;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AdvertisingCard{ ").append(type)
                .append(super.toString())
                .append("}")
                .toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + type.ordinal();
    }

    public CardType getType() {
        return type;
    }
}
