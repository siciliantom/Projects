package by.bsu.parsing.parser;

import by.bsu.parsing.entity.enumTextPartType;
import org.apache.log4j.Logger;
import by.bsu.parsing.entity.Composite;
import by.bsu.parsing.entity.Listing;
import by.bsu.parsing.entity.Symbol;
import by.bsu.parsing.entity.Word;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kate on 14.12.2015.
 */
public class TextParser {
    static Logger logger = Logger.getLogger(TextParser.class);

    private static final String REGEX_PARAGRAPH_WITH_LISTING = "\\p{Blank}{4}";
    private static final String REGEX_LISTING = "(start)[\\w\\p{Punct}\\s]+(end)";
    private static final String REGEX_SENTENCE = "(?<=[\\.!\\?])\\p{Blank}+";
    private final static String REGEX_TOKEN = "(\\p{Blank})";
    private static final String REGEX_SYMBOL = "[.,!?:;]";

    public TextParser() {
    }

    private String initialization(String path) {
        StringBuilder text = new StringBuilder(1000);
        Scanner scanner = null;
        try {
            FileReader fileReader = new FileReader(path);
            scanner = new Scanner(fileReader).useDelimiter("_");
            while (scanner.hasNext()) {
                text.append(scanner.next());
            }
            logger.debug("***File is read successfully ");
            fileReader.close();
        } catch (FileNotFoundException e) {
            logger.fatal("Wrong file input!");
            throw new RuntimeException();
        } catch (IOException e) {
            logger.warn("Smth wrong with closing");
        }
        return text.toString();
    }

    public Composite parse(String path) {
        String text = initialization(path);
        Composite compositeText = new Composite();
        compositeText = parseToParagraph(text);
        return compositeText;
    }

    private Composite parseToParagraph(String text) {
        Composite compositeText = new Composite();
        Composite paragraphComposite = new Composite();
        compositeText.setType(enumTextPartType.TEXT);
        Listing listing = null;
        Pattern patternListing = Pattern.compile(REGEX_LISTING);
        String listingString = "";
        Pattern patternParagraph = Pattern.compile(REGEX_PARAGRAPH_WITH_LISTING);
        String[] splitStringPargraph = patternParagraph.split(text);
        for (String paragraph : splitStringPargraph) {
            Matcher matcher = patternListing.matcher(paragraph);
            if (matcher.find()) {
                listingString = matcher.group();
                listing = new Listing(listingString + "\n");
                listing.setType(enumTextPartType.LISTING);
                logger.debug("***Listing added" + listing);
                compositeText.add(listing);
            } else {
                logger.debug("***Before parsing to sentence, paragraph " + paragraph);
                paragraphComposite = parseToSentense(paragraph + "*");
                logger.debug("***Paragraph added");
                compositeText.add(paragraphComposite);
            }
        }
        return compositeText;
    }

    private Composite parseToSentense(String paragraph) {
        Composite paragraphComposite = new Composite();
        paragraphComposite.setType(enumTextPartType.PARAGRAPH);
        Composite sentenceComposite = new Composite();
        Pattern patternSentence = Pattern.compile(REGEX_SENTENCE);
        String[] splitStringSentence = patternSentence.split(paragraph);
        for (String sentence : splitStringSentence) {
            logger.debug("*** Before parsing to token, SENT# " + sentence);
            sentenceComposite = parseToToken(sentence);
            paragraphComposite.add(sentenceComposite);
        }
        return paragraphComposite;
    }

    private Composite parseToToken(String sentence) {
        Composite tokenComposite = new Composite();
        Composite sentenceComposite = new Composite();
        sentenceComposite.setType(enumTextPartType.SENTENCE);
        String[] splitStringToken = sentence.split(REGEX_TOKEN);
        for (String token : splitStringToken) {
            logger.debug("***TokenList added TOKEN#" + token);
            tokenComposite = parseToWord(token);
            logger.debug("***TokenList added TOKENcomposite " + tokenComposite);
            sentenceComposite.add(tokenComposite);
        }
        logger.debug("***TokenList added TOKENLISTOFSENTENCE# " + sentenceComposite);
        return sentenceComposite;
    }

    private Composite parseToWord(String token) {
        Composite tokenComposite = new Composite();
        tokenComposite.setType(enumTextPartType.TOKEN);
        Pattern patternSymbol = Pattern.compile(REGEX_SYMBOL);
        String wordString = "";
        Matcher matcher = patternSymbol.matcher(token);
        Word word;
        Symbol symbol;
        if (matcher.find()) {
            if (token.charAt(token.length()-1) == '*') {
                symbol = new Symbol(String.valueOf(token.charAt(matcher.start())) + "\n");
            }
            else{
                symbol = new Symbol(String.valueOf(token.charAt(matcher.start())) + " ");
            }
            wordString = token.substring(0, matcher.end() - 1);
            word = new Word(wordString);
            word.setType(enumTextPartType.WORD);
            symbol.setType(enumTextPartType.SYMBOL);
            logger.debug("***Word added# " + word + " symbol# " + symbol);
            tokenComposite.add(word);
            tokenComposite.add(symbol);
        } else {
            logger.debug("***Some word added ");
            Word wordWithoutSign = new Word(token + " ");
            wordWithoutSign.setType(enumTextPartType.WORD);
            tokenComposite.add(wordWithoutSign);
        }
        return tokenComposite;
    }
}
