package com.epam.task2.main;

import com.epam.task2.builder.CardDomBuilder;
import com.epam.task2.builder.CardSaxBuilder;
import com.epam.task2.builder.CardStaxBuilder;
import com.epam.task2.entity.Card;
import com.epam.task2.exception.XmlException;
import com.epam.task2.util.FileResourcesUtil;
import com.epam.task2.validator.XmlValidator;

import java.net.URISyntaxException;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws XmlException, URISyntaxException {
        String fileName = FileResourcesUtil.getFileFromResource("data/stressTest.xml").getAbsolutePath();
        System.out.println(XmlValidator.validate(fileName));
        CardSaxBuilder saxBuilder = new CardSaxBuilder();
        CardDomBuilder domBuilder = new CardDomBuilder();
        CardStaxBuilder staxBuilder = new CardStaxBuilder();
        saxBuilder.buildSetCards(fileName);
        System.out.println("---------------------");
        Set<Card> cardSet = saxBuilder.getCards();
        cardSet.forEach(System.out::println);
        System.out.println("---------------------");
        domBuilder.buildSetCards(fileName);
        Set<Card> cardSet2 = domBuilder.getCards();
        cardSet2.forEach(System.out::println);
        System.out.println("---------------------");
        staxBuilder.buildSetCards(fileName);
        Set<Card> cardSet3 = staxBuilder.getCards();
        cardSet3.forEach(System.out::println);


    }
}
