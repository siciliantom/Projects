package by.bsu.parsing.main;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import by.bsu.parsing.action.SortSentences;
import by.bsu.parsing.action.SwapWords;
import by.bsu.parsing.entity.Composite;
import by.bsu.parsing.parser.TextParser;

import java.util.Collection;

/**
 * Created by Kate on 14.12.2015.
 */
public class CompositeRunner {
    static {
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
    }
    static Logger logger = Logger.getLogger(CompositeRunner.class);

    public static void main(String[] args) {
        String path = "input/text.txt";
        TextParser textParser = new TextParser();
        Composite wholeText = textParser.parse(path);
        logger.debug("***Whole text : " + wholeText.toString());

        Collection sentenceCollection = SortSentences.sortByWordsAmount(wholeText);
        logger.debug("***Sorteded sentences:\n");
        for(Object sentense : sentenceCollection){
            logger.debug(sentense + "\n");
        }

        SwapWords.swapWords(wholeText);
        logger.debug("***Swapped text: " + wholeText.toString());
    }
}
