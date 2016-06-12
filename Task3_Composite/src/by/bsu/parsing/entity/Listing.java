package by.bsu.parsing.entity;

import java.util.ArrayList;

/**
 * Created by Kate on 14.12.2015.
 */
public class Listing implements Component {
    private String listing;
    private enumTextPartType type;

    public Listing(String listing) {
        this.listing = listing;
    }

    public String toString() {
        return listing;
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
