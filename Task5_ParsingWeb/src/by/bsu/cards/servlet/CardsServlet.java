package by.bsu.cards.servlet;

import by.bsu.cards.entity.ArtCard;
import by.bsu.cards.entity.Card;
import by.bsu.cards.parsing.CardsDOMBuilder;
import by.bsu.cards.parsing.CardsSAXBuilder;
import by.bsu.cards.parsing.CardsStAXBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kate on 19.01.2016.
 */
@WebServlet("/chooseAction")
public class CardsServlet extends HttpServlet {
    private static final String FILENAME = "/data/cards.xml";
    private ArrayList<Card> list;
    private ArrayList<Card> artCardsList;
    private ArrayList<Card> docCardsList;
    static Logger logger = Logger.getLogger(CardsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Start processing request");
        artCardsList = new ArrayList<>();
        docCardsList = new ArrayList<>();
        String prefix = getServletContext().getRealPath("/");
        String parseType = request.getParameter("type");
        switch(parseType){
            case "SAXParsing":
                request.setAttribute("res", "Sax");
                CardsSAXBuilder saxBuilder = new CardsSAXBuilder();
                saxBuilder.buildListCards(prefix + FILENAME);
                list = saxBuilder.getCards();
                break;
            case "DOMParsing":
                request.setAttribute("res", "Dom");
                CardsDOMBuilder domBuilder = new CardsDOMBuilder();
                domBuilder.buildListCards(prefix + FILENAME);
                list = domBuilder.getCards();
                break;
            case "StAXParsing":
                request.setAttribute("res", "Stax");
                CardsStAXBuilder staxBuilder = new CardsStAXBuilder();
                staxBuilder.buildListCards(prefix + FILENAME);
                list = staxBuilder.getCards();
                break;
            case "returnBack":
                request.getRequestDispatcher("/jsp/mainChoice.jsp").forward(request, response);
                break;
            default:
                logger.warn("Wrong parsing type!");
                break;
        }
        for(Card card : list) {
            if(card.getClass() == ArtCard.class) {
                artCardsList.add(card);
            }
            else {
                docCardsList.add(card);
            }
        }
        request.setAttribute("artCardList", artCardsList);
        request.setAttribute("docCardList", docCardsList);
        request.getRequestDispatcher("/jsp/parsingResult.jsp").forward(request, response);
    }
}
