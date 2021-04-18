package com.epam.task2.builder;

import com.epam.task2.entity.Card;
import com.epam.task2.entity.CardType;
import com.epam.task2.entity.CardXmlTag;
import com.epam.task2.exception.XmlException;
import com.epam.task2.factory.CardFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardStaxBuilder {
    private final static Logger LOGGER = LogManager.getLogger(CardStaxBuilder.class);

    private final static char DASH = '-';
    private final static char UNDERSCORE_DASH = '_';
    private Set<Card> cards;
    private List<Card.Author> authors;
    private Card.Author author;
    private XMLInputFactory inputFactory;

    public CardStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
        cards = new HashSet<>();
        authors = new ArrayList<>();
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void buildSetCards(String fileName) {
        LOGGER.log(Level.INFO, "parsing file " + fileName);
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream =
                     new FileInputStream(new File(fileName))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(CardXmlTag.CARD.getValue())) {
                        Card card = buildCard(reader);
                        cards.add(card);
                    }
                }
            }
        } catch (IOException | XMLStreamException | XmlException e) {
            LOGGER.log(Level.WARN, e);
        }
        StringBuilder logMessage = new StringBuilder("end parsing file ... objects : {\n");
        for (Card card : cards) {
            logMessage.append(card).append('\n');
        }
        logMessage.append("}");
        LOGGER.log(Level.INFO, logMessage);
    }

    private Card buildCard(XMLStreamReader reader) throws XmlException, XMLStreamException {
        String typeCard = reader.getAttributeValue(null, CardXmlTag.CARD_TYPE.getValue());
        CardType cardType = CardType.valueOf(typeCard.replace(DASH, UNDERSCORE_DASH).toUpperCase());
        Card card = CardFactory.CreateCardByType(cardType);

        String idString = reader.getAttributeValue(null, CardXmlTag.ID.getValue());
        idString = idString.split("(?<=\\D)(?=\\d)")[1];
        int id = Integer.parseInt(idString);
        boolean send = false;
        send = Boolean.parseBoolean(reader.getAttributeValue(null, CardXmlTag.SEND.getValue()));
        card.setId(id);

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT: {
                    name = reader.getLocalName();
                    switch (CardXmlTag.valueOf(name.toUpperCase().replace(DASH, UNDERSCORE_DASH))) {
                        case THEM: {
                            card.setThem(getXmlText(reader));
                        }
                        break;
                        case COUNTRY: {
                            card.setCountry(getXmlText(reader));
                        }
                        break;
                        case DATE: {
                            LocalDate date = LocalDate.parse(getXmlText(reader));
                            card.setDate(date);
                        }
                        break;
                        case VALUABLE: {
                            card.setValuable(getXmlText(reader));
                        }
                        break;
                        case AUTHOR: {
                            author = new Card.Author();
                        }
                        break;
                        case NAME: {
                            author.setName(getXmlText(reader));
                        }
                        break;
                        case SER_NAME: {
                            author.setSerName(getXmlText(reader));
                        }
                        break;
                    }
                }
                break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    CardXmlTag tag = CardXmlTag.valueOf(name.toUpperCase().replace(DASH, UNDERSCORE_DASH));
                    if (tag == CardXmlTag.CARD) {
                        card.setAuthors(authors);
                        authors = new ArrayList<>();
                        return card;
                    } else if (tag == CardXmlTag.AUTHOR) {
                        authors.add(author);
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <card>");
    }

    private String getXmlText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}

