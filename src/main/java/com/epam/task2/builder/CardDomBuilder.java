package com.epam.task2.builder;

import com.epam.task2.entity.Card;
import com.epam.task2.entity.CardType;
import com.epam.task2.entity.CardXmlTag;
import com.epam.task2.exception.XmlException;
import com.epam.task2.factory.CardFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardDomBuilder {
    private final static Logger LOGGER = LogManager.getLogger(CardDomBuilder.class);
    private Set<Card> cards;
    private DocumentBuilder docBuilder;

    public CardDomBuilder() {
        cards = new HashSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.WARN, e);
        }

    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }

    private static List<Card.Author> getAuthorsFromCard(Element element) {
        NodeList authorsList = element.getElementsByTagName(CardXmlTag.AUTHOR.getValue());
        List<Card.Author> listAuthor = new ArrayList<>();
        for (int i = 0; i < authorsList.getLength(); i++) {
            Element authorElement = (Element) authorsList.item(i);
            Card.Author author = new Card.Author();
            author.setName(getElementTextContent(authorElement, CardXmlTag.NAME.getValue()));
            author.setSerName(getElementTextContent(authorElement, CardXmlTag.SER_NAME.getValue()));
            listAuthor.add(author);
        }
        return listAuthor;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void buildSetCards(String fileName) {
        LOGGER.log(Level.INFO, "parsing file " + fileName);
        Document doc;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList cardsList = root.getElementsByTagName("Card");
            for (int i = 0; i < cardsList.getLength(); i++) {
                Element cardElement = (Element) cardsList.item(i);
                Card card = buildCard(cardElement);
                cards.add(card);
            }
        } catch (IOException | SAXException | XmlException e) {
            LOGGER.log(Level.WARN, e);
        }
        StringBuilder logMessage = new StringBuilder("end parsing file ... objects : {\n");
        for (Card card : cards) {
            logMessage.append(card).append('\n');
        }
        logMessage.append("}");
        LOGGER.log(Level.INFO, logMessage);
    }

    private Card buildCard(Element cardElement) throws XmlException {
        Card card;
        String typeCard = cardElement.getAttribute(CardXmlTag.CARD_TYPE.getValue()).toUpperCase();
        CardType type = CardType.valueOf(typeCard);
        card = CardFactory.CreateCardByType(type);

        String idString = cardElement.getAttribute(CardXmlTag.ID.getValue());
        String intPart = idString.split("(?<=\\D)(?=\\d)")[1];
        int id = Integer.parseInt(intPart);
        card.setId(id);

        String send = cardElement.getAttribute(CardXmlTag.SEND.getValue());
        boolean sendBoolean = Boolean.parseBoolean(send);
        card.setSend(sendBoolean);

        card.setThem(getElementTextContent(cardElement, CardXmlTag.THEM.getValue()));
        card.setCountry(getElementTextContent(cardElement, CardXmlTag.COUNTRY.getValue()));
        String date = getElementTextContent(cardElement, CardXmlTag.DATE.getValue());
        LocalDate data = LocalDate.parse(date);
        card.setDate(data);
        card.setValuable(getElementTextContent(cardElement, CardXmlTag.VALUABLE.getValue()));
        List<Card.Author> authorList = getAuthorsFromCard(cardElement);
        card.setAuthors(authorList);
        return card;
    }
}
