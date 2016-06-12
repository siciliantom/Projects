package by.bsu.cards.parsing;

import by.bsu.cards.entity.Card;
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

    public CardsSAXBuilder() {
        handler = new CardsSAXHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
        } catch (SAXException e) {
            System.err.print("ошибка SAX парсера: " + e);
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void buildListCards(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            System.err.print("ошибка SAX парсера: " + e);
        } catch (IOException e) {
            System.err.print("ошибка I/О потока: " + e);
        }
        cards = handler.getCards();
    }
}

