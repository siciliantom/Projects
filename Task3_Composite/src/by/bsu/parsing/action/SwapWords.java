package by.bsu.parsing.action;

import by.bsu.parsing.entity.enumTextPartType;
import org.apache.log4j.Logger;
import by.bsu.parsing.entity.Component;
import by.bsu.parsing.entity.Composite;
import by.bsu.parsing.entity.Word;

import java.util.List;

/**
 * Created by Kate on 30.12.2015.
 */
//swap first and last word in sentance
public class SwapWords {
    static Logger logger = Logger.getLogger(SwapWords.class);

    public static void swapWords(Composite text) {
        for(int i = 0; i < text.getComponents().size(); i++) {
            if(text.getChild(i).getType() != enumTextPartType.LISTING) {
                for(int j = 0; j < text.getComponents().get(i).getSize(); j++) {
                    List<Component> list = text.getChild(i).getChild(j).getComponents();
                    Component firstWord = list.get(0).getComponents().get(0);//0-because first
                    firstWord = new Word(firstWord.toString().toLowerCase().trim());

                    Component lastWord = list.get(list.size() - 1).getComponents().get(list.get(list.size() - 1).getComponents().size() - 2);
                    String lastWordString = lastWord.toString();
                    String resultWord = lastWordString.replace(lastWordString.substring(0, 1).charAt(0), lastWordString.substring(0, 1).toUpperCase().charAt(0));
                    lastWord = new Word(resultWord + " ");
                    list.get(list.size() - 1).getComponents().set(list.get(list.size() - 1).getComponents().size() - 2, firstWord);
                    list.get(0).getComponents().set(0, lastWord);//tokens
                }
            }
        }
    }
}
