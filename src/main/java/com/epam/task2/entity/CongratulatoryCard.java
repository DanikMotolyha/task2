package com.epam.task2.entity;

import java.time.LocalDate;
import java.util.List;

public class CongratulatoryCard extends Card {
    private CardType type = CardType.CONGRATULATORY;

    public CongratulatoryCard(int id, String them, boolean send, String country, LocalDate date, List<Author> authors, String valuable) {
        super(id, them, send, country, date, authors, valuable);
    }

    public CongratulatoryCard() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CongratulatoryCard that = (CongratulatoryCard) o;
        return type == that.type;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CongratulatoryCard{ ").append(type)
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
