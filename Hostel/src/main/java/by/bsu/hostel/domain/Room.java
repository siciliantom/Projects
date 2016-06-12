package by.bsu.hostel.domain;

/**
 * Created by Kate on 13.02.2016.
 */
public class Room extends Entity{
    private int maxPlaces;
    private int freePlaces;
    private int price;
    private String type;

    public Room(){}

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "maxPlaces=" + maxPlaces +
                ", freePlaces=" + freePlaces +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
