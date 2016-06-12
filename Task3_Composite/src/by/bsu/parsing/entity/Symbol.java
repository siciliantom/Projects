package by.bsu.parsing.entity;

import java.util.ArrayList;

/**
 * Created by Kate on 16.12.2015.
 */
public class Symbol implements Component {
    private String symbol;
    private enumTextPartType type;

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return symbol;
    }

    public enumTextPartType getType() {
        return type;
    }

    @Override
    public Component getChild(int index) {
        return null;
    }

    @Override
    public ArrayList<Component> getComponents() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    public void setType(enumTextPartType type) {
        this.type = type;
    }
}
