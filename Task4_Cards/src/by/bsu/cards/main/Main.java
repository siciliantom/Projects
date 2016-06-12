package by.bsu.cards.main;

import by.bsu.cards.parsing.CardsStAXBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Kate on 01.01.2016.
 */
public class Main {
//    static {
//        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
//    }
//    static Logger logger = org.apache.log4j.Logger.getLogger(Main.class);

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        //sax
//        CardsSAXBuilder saxBuilder = new CardsSAXBuilder();
//        saxBuilder.buildListCards("data/cards.xml");
//        System.out.println(saxBuilder.getCards().get(11));

        //dom
//        CardsDOMBuilder domBuilder = new CardsDOMBuilder();
//        domBuilder.buildListCards("data/cards.xml");
//        System.out.println(domBuilder.getCards());
        //stax
        CardsStAXBuilder staxBuilder = new CardsStAXBuilder();
        staxBuilder.buildListCards("data/cards.xml");
        System.out.println(staxBuilder.getCards());
    }
}
