package com.epam.task2.factory;

import com.epam.task2.entity.AdvertisingCard;
import com.epam.task2.entity.Card;
import com.epam.task2.entity.CardType;
import com.epam.task2.entity.CongratulatoryCard;
import com.epam.task2.exception.XmlException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardFactory {
    private final static Logger LOGGER = LogManager.getLogger(CardFactory.class);

    public static Card CreateCardByType(CardType type) throws XmlException {
        Card card;
        switch (type) {
            case ADVERTISING: {
                card = new AdvertisingCard();
            }
            break;
            case CONGRATULATORY: {
                card = new CongratulatoryCard();
            }
            break;
            default: {
                throw new XmlException("Unexpected value: " + type);
            }
        }
        LOGGER.log(Level.INFO, "Empty typed card created : " + card);
        return card;
    }
}
