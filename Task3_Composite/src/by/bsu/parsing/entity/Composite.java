package by.bsu.parsing.entity;

/**
 * Created by Kate on 14.12.2015.
 */

import java.util.ArrayList;

public class Composite implements Component {
    private ArrayList<Component> components = new ArrayList<Component>();
    private enumTextPartType type;

    public String toString() {
        StringBuilder textOutput = new StringBuilder(1000);
        for (Component component : components) {
            textOutput.append(component.toString());
        }
        return textOutput.toString();
    }

    public boolean add(Component component) {
        return components.add(component);
    }

    public boolean remove(Component component) {
        return components.remove(component);
    }

    public Component getChild(int index) {
        return components.get(index);
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public enumTextPartType getType() {
        return type;
    }

    public void setType(enumTextPartType type) {
        this.type = type;
    }

    public int getSize() {
        return components.size();
    }
}
