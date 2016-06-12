package by.bsu.hostel.domain;


import java.sql.Date;

/**
 * Created by Kate on 13.02.2016.
 */
public class Application extends Entity {
    private int placesAmount;
    private Date arrivalDate;
    private Date departureDate;
    private ConfirmationEnum confirmed;
    private int finalPrice;
    private Long clientId;
    private Room room;

    public Application() {
        this.room = new Room();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long client_id) {
        this.clientId = client_id;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public int getPlacesAmount() {
        return placesAmount;
    }

    public void setPlacesAmount(int placesAmount) {
        this.placesAmount = placesAmount;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ConfirmationEnum getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(ConfirmationEnum confirmed) {
        this.confirmed = confirmed;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    @Override
    public String toString() {
        return "Application{" +
                "placesAmount=" + placesAmount +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", confirmed=" + confirmed +
                ", finalPrice=" + finalPrice +
                ", clientId=" + clientId +
                ", room=" + room +
                '}';
    }
}
