package com.epam.task2.builder;

import com.epam.task2.entity.Card;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class CardSaxBuilder {
    private final static Logger LOGGER = LogManager.getLogger(CardSaxBuilder.class);
    private Set<Card> cards;
    private CardHandler handler = new CardHandler();
    private XMLReader reader;

    public CardSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newDefaultInstance();
        try{
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
            reader.setContentHandler(handler);
        } catch (ParserConfigurationException | SAXException e){
            LOGGER.log(Level.WARN, e.getMessage());
        }
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void buildSetCards(String fileName) {
        LOGGER.log(Level.INFO, "parsing file " + fileName);
        try {
            reader.parse(fileName);
        }catch (IOException | SAXException e){
            LOGGER.warn("with file name : " + fileName + " , details :", e);
        }
        cards = handler.getCards();
        StringBuilder logMessage = new StringBuilder("end parsing file ... objects : {\n");
        for (Card card : cards) {
            logMessage.append(card).append('\n');
        }
        logMessage.append("}");
        LOGGER.log(Level.INFO, logMessage);
    }
}
