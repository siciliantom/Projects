package by.bsu.parsing.action;

import by.bsu.parsing.entity.enumTextPartType;
import org.apache.log4j.Logger;
import by.bsu.parsing.entity.Component;
import by.bsu.parsing.entity.Composite;

import java.util.*;

/**
 * Created by Kate on 31.12.2015.
 */
//sort sentences by word amount
public class SortSentences {
    static Logger logger = Logger.getLogger(SortSentences.class);

    public static Collection sortByWordsAmount(final Composite text) {
        Map<Integer, Component> sentencesMap = new TreeMap<Integer, Component>();
        for (int i = 0; i < text.getComponents().size(); i++) {
            if (text.getChild(i).getType() != enumTextPartType.LISTING) {
                for (int j = 0; j < text.getComponents().get(i).getSize(); j++) {
                    Component sentence = text.getChild(i).getChild(j);
                    //logger.debug("***Sent!!!!!!!!!!!!__ " + sentence);
                    Integer wordCounter = 0;
                    for (Component comp : sentence.getComponents()) {//tokens
                        if (comp.getChild(0).getType() == enumTextPartType.WORD) {
                            wordCounter++;
                        }
                    }
                    //logger.debug("***counter###########__ " + wordCounter);
                    sentencesMap.put(wordCounter, sentence);
                }
            }
        }
        return sentencesMap.values();
    }
}
