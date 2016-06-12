package by.bsu.cards.entity;

/**
 * Created by Kate on 14.01.2016.
 */
public enum CardEnum {
        OLD_CARDS("ald-cards"),
        ART_CARD("art-card"),
        DOCUMENTARY_CARD("documentary-card"),
        TYPE("type"),
        POSTED("posted"),
        THEME("theme"),
        REGISTRY("registry"),
        YEAR("year"),
        TELEPHONE("telephone"),
        COUNTRY("country"),
        VALUABLE("valuable"),
        NAME("name"),
        AUTHOR("author"),
        YEAR_OF_SHOT("year-of-shot"),
        PHOTO("photo"),
        PICTURE("picture");
        private String value;
        private CardEnum(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static CardEnum getEnum(String value) {
            for(CardEnum currentEnum : CardEnum.values()){
                if(currentEnum.getValue() == value) {
                    return currentEnum;
                }
            }
            return null;
        }
}
