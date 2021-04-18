package com.epam.task2.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Card {
    private int id;
    private String them;
    private boolean send;
    private String country;
    private LocalDate date;
    private List<Author> authors;
    private String valuable;

    public Card() {
        authors = new ArrayList<>();
    }

    public Card(int id, String them,
                boolean send, String country, LocalDate date,
                List<Author> authors, String valuable) {
        this.id = id;
        this.them = them;
        this.send = send;
        this.country = country;
        this.date = date;
        this.authors = authors;
        this.valuable = valuable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && send == card.send && them.equals(card.them) &&
                country.equals(card.country) && date.equals(card.date) &&
                authors.equals(card.authors) && valuable.equals(card.valuable);
    }

    @Override
    public int hashCode() {
        return id + date.hashCode();
    }

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThem() {
        return them;
    }

    public void setThem(String them) {
        this.them = them;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getValuable() {
        return valuable;
    }

    public void setValuable(String valuable) {
        this.valuable = valuable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" Card{");
        sb.append("id=").append(id);
        sb.append(", them='").append(them).append('\'');
        sb.append(", send=").append(send);
        sb.append(", country='").append(country).append('\'');
        sb.append(", date=").append(date);
        sb.append(", authors=").append(authors);
        sb.append(", valuable='").append(valuable).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class Author {
        private String name;
        private String serName;

        public Author() {
        }

        public Author(String name, String serName) {
            this.name = name;
            this.serName = serName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSerName() {
            return serName;
        }

        public void setSerName(String serName) {
            this.serName = serName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Author author = (Author) o;
            return Objects.equals(name, author.name) &&
                    Objects.equals(serName, author.serName);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Author{");
            sb.append("name='").append(name).append('\'');
            sb.append(", serName='").append(serName).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public int hashCode() {
            int result = 31;
            result += name.length() * serName.length();
            return result;
        }
    }
}
