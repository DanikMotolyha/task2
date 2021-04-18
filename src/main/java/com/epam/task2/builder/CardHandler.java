package com.epam.task2.builder;

import com.epam.task2.entity.Card;
import com.epam.task2.entity.CardType;
import com.epam.task2.entity.CardXmlTag;
import com.epam.task2.exception.XmlException;
import com.epam.task2.factory.CardFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.*;

public class CardHandler extends DefaultHandler {
    private final static Logger LOGGER = LogManager.getLogger(CardHandler.class);
    private final static char DASH = '-';
    private final static char UNDERSCORE_DASH = '_';
    private Set<Card> cards;
    private Card card;
    private List<Card.Author> authors;
    private Card.Author author;
    private CardXmlTag currentXmlTag;
    private EnumSet<CardXmlTag> withText;

    public CardHandler() {
        cards = new HashSet<>();
        authors = new ArrayList<>();
        withText = EnumSet.range(CardXmlTag.THEM, CardXmlTag.VALUABLE);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (CardXmlTag.CARD.getValue().equals(qName)) {
            int id = -1;
            CardType cardType;
            boolean send = false;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                String attribute = attributes.getQName(i)
                        .replace(DASH, UNDERSCORE_DASH).toUpperCase();
                CardXmlTag tag = CardXmlTag.valueOf(attribute);
                String value = attributes.getValue(i);
                switch (tag) {
                    case ID: {
                        String intPart = value.split("(?<=\\D)(?=\\d)")[1];
                        id = Integer.parseInt(intPart);
                    }
                    break;
                    case CARD_TYPE: {
                        cardType = CardType.valueOf(value.toUpperCase());
                        try {
                            card = CardFactory.CreateCardByType(cardType);
                        } catch (XmlException e) {
                            LOGGER.log(Level.WARN, e);
                        }
                    }
                    break;
                    case SEND: {
                        send = Boolean.getBoolean(value);
                    }
                    break;
                }
            }
            card.setId(id);
            card.setSend(send);
        } else if (CardXmlTag.AUTHOR.getValue().equals(qName)) {
            author = new Card.Author();
        }
        CardXmlTag temp = CardXmlTag.valueOf(qName.toUpperCase().replace(DASH, UNDERSCORE_DASH));
        if (withText.contains(temp)) {
            currentXmlTag = temp;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case THEM: {
                    card.setThem(data);
                }
                break;
                case COUNTRY: {
                    card.setCountry(data);
                }
                break;
                case DATE: {
                    LocalDate date = LocalDate.parse(data);
                    card.setDate(date);
                }
                break;
                case NAME: {
                    author.setName(data);
                }
                break;
                case SER_NAME: {
                    author.setSerName(data);
                }
                case VALUABLE: {
                    card.setValuable(data);
                }
                break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        currentXmlTag = null;
        if (CardXmlTag.AUTHOR.getValue().equals(qName)) {
            authors.add(author);
        }
        if (CardXmlTag.CARD.getValue().equals(qName)) {
            card.setAuthors(authors);
            cards.add(card);
            authors = new ArrayList<>();
        }
    }

    public Set<Card> getCards() {
        return cards;
    }
}
