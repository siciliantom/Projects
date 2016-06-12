package by.bsu.cards.parsing;

/**
* Created by Kate on 19.01.2016.
*/

import by.bsu.cards.entity.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
public class CardsStAXBuilder {
    private ArrayList<Card> cards;
    private XMLInputFactory inputFactory;
    Card card = null;

    public CardsStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
        cards = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void buildListCards(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
// StAX parsing
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    System.out.println("name. " + name);
                    if (CardEnum.getEnum(name) == CardEnum.ART_CARD) {
                        card = new ArtCard();
                        ArtCard artCard = new ArtCard();
                        artCard = (ArtCard)buildCard(reader);
                        cards.add(artCard);
                    }
                    if (CardEnum.getEnum(name) == CardEnum.DOCUMENTARY_CARD) {
                        card = new DocumentaryCard();
                        DocumentaryCard docCard = new DocumentaryCard();
                        docCard =(DocumentaryCard)buildCard(reader);
                        cards.add(docCard);
                    }

                }
            }
        } catch (XMLStreamException ex) {
            System.err.println("StAX parsing error! " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.err.println("File " + fileName + " not found! " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Impossible close file "+fileName+" : "+e);
            }
        }
    }

    private Card buildCard(XMLStreamReader reader) throws XMLStreamException {
       card.setType(reader.getAttributeValue(null, CardEnum.TYPE.getValue()));
       card.setRegistry(reader.getAttributeValue(null,CardEnum.REGISTRY.getValue())); // проверить на null
       String name;
       while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    System.out.println("name " + name);
                    switch (CardEnum.getEnum(name)) {
                        case THEME:
                           card.setTheme(getXMLText(reader));
                            break;
                        case COUNTRY:
                           card.setCountry(getXMLText(reader));
                            break;
                        case YEAR:
                           card.setYear(getXMLText(reader));
                            break;
                        case VALUABLE:
                           card.setValuable(getXMLText(reader));
                            break;
                        case POSTED:
                           card.setPosted(new Boolean(getXMLText(reader)));
                            break;
                        case PICTURE:
                            ((ArtCard)card).setPicture(getXMLPicture(reader));
                            break;
                        case PHOTO:
                            ((DocumentaryCard)card).setPhoto(getXMLPhoto(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (CardEnum.getEnum(name) == CardEnum.ART_CARD){
                        System.out.println(" " + ((ArtCard)card).getPicture());
                        return card;
                    }
                    if(CardEnum.getEnum(name) == CardEnum.DOCUMENTARY_CARD) {
                        System.out.println(" " + ((DocumentaryCard)card).getPhoto());
                        return card;
                     }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Card");
    }
    private Picture getXMLPicture(XMLStreamReader reader) throws XMLStreamException {
        Picture picture = new Picture();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (CardEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            picture.setName(getXMLText(reader));
                            break;
                        case AUTHOR:
                            picture.setAuthor(getXMLText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (CardEnum.valueOf(name.toUpperCase()) == CardEnum.PICTURE){
                        return picture;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Address");
    }

    private Photo getXMLPhoto(XMLStreamReader reader) throws XMLStreamException {
        Photo photo = new Photo();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (CardEnum.getEnum(name)) {
                        case NAME:
                            photo.setName(getXMLText(reader));
                            break;
                        case AUTHOR:
                            photo.setAuthor(getXMLText(reader));
                            break;
                        case YEAR_OF_SHOT:
                            photo.setYearOfShot(getXMLText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (CardEnum.getEnum(name) == CardEnum.PHOTO){
                        return photo;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Address");
    }
    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
