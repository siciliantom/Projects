package by.bsu.cards.parsing;

/**
 * Created by Kate on 18.01.2016.
 */
import by.bsu.cards.entity.*;
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

    public CardsDOMBuilder() {
        this.cards = new ArrayList<Card>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println("Ошибка конфигурации парсера: " + e);
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void buildListCards(String fileName) {
        Document doc = null;
        try {
            doc = docBuilder.parse(fileName);
           // System.out.println("buildList " +doc.getDocumentElement());
            Element root = doc.getDocumentElement();
            //System.out.println("root " + root);
            NodeList artCardsList = root.getElementsByTagName("tns:art-card");
            NodeList docCardsList = root.getElementsByTagName("tns:documentary-card");
            //System.out.println("list " + cardsList.getLength());
            for (int i = 0; i < artCardsList.getLength(); i++) {
                Element cardElement = (Element)artCardsList.item(i);
                card = new ArtCard();
                isArtCard = true;
                ArtCard artCard = (ArtCard)buildCard(cardElement);
                //System.out.println(((ArtCard)card).getPicture().toString());
                cards.add(artCard);
            }
            for (int i = 0; i < docCardsList.getLength(); i++) {
                Element cardElement = (Element)docCardsList.item(i);
                card = new DocumentaryCard();
                isArtCard = false;
                DocumentaryCard docCard = (DocumentaryCard)buildCard(cardElement);
                //System.out.println(((ArtCard)card).getPicture().toString());
                cards.add(docCard);
            }
        } catch (IOException e) {
            System.err.println("File error or I/O error: " + e);
        } catch (SAXException e) {
            System.err.println("Parsing failure: " + e);
        }
    }
    private Card buildCard(Element cardElement) {
        Picture picture = null;
        Photo photo = null;
        card.setType(cardElement.getAttribute("type")); // проверка на null
        //System.out.println("elem " + cardElement.getAttribute("type"));
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
    // получение текстового содержимого тега
    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName("tns:" + elementName);
        //System.out.println("list * " + nList.getLength() + " " +element+elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
