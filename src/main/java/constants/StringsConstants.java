package constants;

public class StringsConstants {
    public static final String DECIMAL_REGEX = "[0-9]+[\\.]?[0-9]*";
    public static final String NUMBER_REGEX = "[0-9]+";
    public static final String NAME_REGEX = "[a-zA-Z\\s]+";
    public static final String ID_REGEX = "#([0-9]+)#";
    public static final String DATE_REGEX = "[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}";


    // TODO: public static final String INDEX_OUT_OF_RANGE = "%s index is out of range";
    public static final String INVALID_FORM_ALERT_TITLE = "Invalid form";
    public static final String INVALID_FORM_ALERT_CONTENT = "Please fill in the form correctly!";
    public static final String INVALID_DATA_ALERT_TITLE = "Invalid data";
    public static final String QUANTITY_SHORTAGE_ALERT_TITLE = "There is a shortage of an item...";
    public static final String QUANTITY_SHORTAGE_ALERT_CONTENT = "There is a shortage of %s at the moment (possibly expired also)";
    public static final String ERROR_ALERT_TITLE = "Oh, no! An error...";
    public static final String BALANCE_SHORT_TITLE = "Oh, no! Seems like you are broke...";
    public static final String BALANCE_SHORT_CONTENT = "You are short by %.2f lv.";
    public static final String ITEM_MUST_BE_INSTANCE_ADDING = "Item must be of class %s when adding to %s";
    public static final String ITEM_MUST_BE_INSTANCE_REMOVING = "Item must be of class %s when removing from %s";
    public static final String CLASS_MUST_NOT_BE_NULL = "Class %s must not be null";
    public static final String ITEM_ALREADY_EXISTS = "%s with identity number #%d already exists!";


    public static final String SEPARATOR = "------------------------";
}
