package by.bsu.parsing.entity;

import java.util.ArrayList;

/**
 * Created by Kate on 14.12.2015.
 */
public interface Component {
    String toString();
    enumTextPartType getType();
    Component getChild(int index);
    ArrayList<Component> getComponents();
    int getSize();
}
