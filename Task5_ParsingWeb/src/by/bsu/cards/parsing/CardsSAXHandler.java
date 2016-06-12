package by.bsu.cards.parsing;

import by.bsu.cards.entity.*;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Kate on 18.01.2016.
 */
public class CardsSAXHandler extends DefaultHandler{
    public final int AMOUNT_OF_ATTRIBUTES = 2;
    private CardEnum currentEnum = null;
    private CardEnum thisElementEnum = null;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Picture picture = null;
    private Photo photo = null;
    private CardEnum subClassEnum = null;
    Card card = null;
    static Logger logger = Logger.getLogger(CardsSAXHandler.class);

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public void startDocument() throws SAXException {
        logger.debug("Start parsing...");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
        thisElementEnum = CardEnum.getEnum(localName);
        System.out.println("--"+localName + " " + attributes.getLength());
        if (thisElementEnum  != null) {
            switch (thisElementEnum) {
                case ART_CARD:
                    card = new ArtCard();
                    subClassEnum = CardEnum.ART_CARD;
                    break;
                case DOCUMENTARY_CARD:
                    card = new DocumentaryCard();
                    subClassEnum = CardEnum.DOCUMENTARY_CARD;
                    break;
                default:
                    currentEnum = CardEnum.getEnum(localName);
                    break;
            }
            if (attributes.getLength() == AMOUNT_OF_ATTRIBUTES) {
                card.setType(attributes.getValue(0));
                card.setRegistry(attributes.getValue(1));
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String stringFromElement = new String(ch, start, length).trim();
        if(currentEnum != null) {
            switch (currentEnum) {
                case THEME:
                    card.setTheme(stringFromElement);
                    break;
                case COUNTRY:
                    card.setCountry(stringFromElement);
                    break;
                case YEAR:
                    card.setYear(stringFromElement);
                    break;
                case POSTED:
                    card.setPosted(new Boolean(stringFromElement));
                    break;
                case VALUABLE:
                    card.setValuable(stringFromElement);
                    break;
                case PICTURE:
                    picture = new Picture();
                    break;
                case PHOTO:
                    photo = new Photo();
                    break;
                case NAME:
                    if (subClassEnum == CardEnum.ART_CARD) {
                        picture.setName(stringFromElement);
                    } else {
                        photo.setName(stringFromElement);
                    }
                    break;
                case AUTHOR:
                    if (subClassEnum == CardEnum.ART_CARD) {
                        picture.setAuthor(stringFromElement);
                    } else {
                        photo.setAuthor(stringFromElement);
                    }
                    break;
                case YEAR_OF_SHOT:
                    photo.setYearOfShot(stringFromElement);
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentEnum.getDeclaringClass(), currentEnum.name());
            }
            currentEnum = null;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElementEnum = CardEnum.getEnum(localName);
        if(thisElementEnum != null) {
            switch (thisElementEnum) {
                case PICTURE:
                    ((ArtCard)card).setPicture(picture);
                    break;
                case PHOTO:
                    ((DocumentaryCard) card).setPhoto(photo);
                    break;
                case ART_CARD: case DOCUMENTARY_CARD:
                    cards.add(card);
                    subClassEnum = null;
                    break;
            }
        }
        thisElementEnum = null;
    }

    @Override
    public void endDocument() {
        logger.debug("Stop parsing...");
    }
}
