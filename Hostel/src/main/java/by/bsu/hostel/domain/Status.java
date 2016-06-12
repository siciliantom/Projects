package by.bsu.hostel.domain;

/**
 * Created by Kate on 18.03.2016.
 */
public class Status extends Entity {
    private int visits;
    private ConfirmationEnum banned;

    public Status() {
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public ConfirmationEnum getBanned() {
        return banned;
    }

    public void setBanned(ConfirmationEnum banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "Status{" +
                "visits=" + visits +
                ", banned=" + banned +
                '}';
    }
}
