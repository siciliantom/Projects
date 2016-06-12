package by.bsu.cards.parsing;

import by.bsu.cards.entity.Card;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kate on 17.01.2016.
 */
public class CardsSAXBuilder {
    private ArrayList<Card> cards;
    private CardsSAXHandler handler;
    private XMLReader reader;
    static Logger logger = Logger.getLogger(CardsSAXBuilder.class);

    public CardsSAXBuilder() {
        handler = new CardsSAXHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
        } catch (SAXException e) {
            logger.error("Wrong sax parsing!" + e);
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void buildListCards(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            logger.error("Wrong sax parsing!" + e);
        } catch (IOException e) {
            logger.error("Wrong work with IO!" + e);
        }
        cards = handler.getCards();
    }
}

