package by.bsu.cards.parsing;

/**
 * Created by Kate on 18.01.2016.
 */
import by.bsu.cards.entity.*;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class CardsDOMBuilder {
    private ArrayList<Card> cards;
    private DocumentBuilder docBuilder;
    private Card card = null;
    private boolean isArtCard;
    static Logger logger = Logger.getLogger(CardsDOMBuilder.class);

    public CardsDOMBuilder() {
        this.cards = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
           logger.error("Wrong parsing!" + e);
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void buildListCards(String fileName) {
        Document doc;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList artCardsList = root.getElementsByTagName("tns:art-card");
            NodeList docCardsList = root.getElementsByTagName("tns:documentary-card");
            for (int i = 0; i < artCardsList.getLength(); i++) {
                Element cardElement = (Element)artCardsList.item(i);
                card = new ArtCard();
                isArtCard = true;
                ArtCard artCard = (ArtCard)buildCard(cardElement);
                cards.add(artCard);
            }
            for (int i = 0; i < docCardsList.getLength(); i++) {
                Element cardElement = (Element)docCardsList.item(i);
                card = new DocumentaryCard();
                isArtCard = false;
                DocumentaryCard docCard = (DocumentaryCard)buildCard(cardElement);
                cards.add(docCard);
            }
        } catch (IOException e) {
            logger.error("Wrong parsing!" + e);
        } catch (SAXException e) {
            logger.error("Wrong dom parsing!" + e);
        }
    }
    private Card buildCard(Element cardElement) {
        Picture picture;
        Photo photo;
        card.setType(cardElement.getAttribute("type"));
        card.setRegistry(cardElement.getAttribute("registry"));
        card.setTheme(getElementTextContent(cardElement, "theme"));
        card.setCountry(getElementTextContent(cardElement, "country"));
        card.setValuable(getElementTextContent(cardElement, "valuable"));
        card.setYear(getElementTextContent(cardElement, "year"));
        card.setPosted(new Boolean(getElementTextContent(cardElement, "posted")));
        if(isArtCard){
            Element pictureElement = (Element)cardElement.getElementsByTagName("tns:picture").item(0);
            picture = new Picture();
            picture.setName(getElementTextContent(pictureElement, "name"));
            picture.setAuthor(getElementTextContent(pictureElement, "author"));
            ((ArtCard)card).setPicture(picture);
        }
        else{
            Element photoElement = (Element)cardElement.getElementsByTagName("tns:photo").item(0);
            photo = new Photo();
            photo.setName(getElementTextContent(photoElement, "name"));
            photo.setAuthor(getElementTextContent(photoElement, "author"));
            photo.setYearOfShot(getElementTextContent(photoElement, "year-of-shot"));
            ((DocumentaryCard)card).setPhoto(photo);
        }
        return card;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName("tns:" + elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
