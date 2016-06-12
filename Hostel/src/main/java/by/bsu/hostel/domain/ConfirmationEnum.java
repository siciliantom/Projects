package by.bsu.hostel.domain;

/**
 * Created by Kate on 27.03.2016.
 */
public enum ConfirmationEnum {
    NO,
    YES;

    public static ConfirmationEnum valueOf(int value) {
        for (ConfirmationEnum item : values()) {
            if (item.ordinal() == value) {
                return item;
            }
        }
        return null;
    }
}


