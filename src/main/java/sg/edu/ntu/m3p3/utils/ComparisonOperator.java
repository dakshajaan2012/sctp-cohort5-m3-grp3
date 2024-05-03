package sg.edu.ntu.m3p3.utils;

public enum ComparisonOperator {
    EQUALS,
    NOT_EQUALS,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    CONTAINS,
    STARTS_WITH,
    ENDS_WITH,
    IS,
    IS_NOT,
    ON_OR_AFTER,
    ON_OR_BEFORE;

    public static ComparisonOperator fromString(String text) {
        switch (text) {
            case "EQUALS":
                return EQUALS;
            case "NOT_EQUALS":
                return NOT_EQUALS;
            case "GREATER_THAN":
                return GREATER_THAN;
            case "GREATER_THAN_OR_EQUAL":
                return GREATER_THAN_OR_EQUAL;
            case "LESS_THAN":
                return LESS_THAN;
            case "LESS_THAN_OR_EQUAL":
                return LESS_THAN_OR_EQUAL;
            case "CONTAINS":
                return CONTAINS;
            case "STARTS_WITH":
                return STARTS_WITH;
            case "ENDS_WITH":
                return ENDS_WITH;
            case "IS":
                return IS;
            case "IS_NOT":
                return IS_NOT;
            case "ON_OR_AFTER":
                return ON_OR_AFTER;
            case "ON_OR_BEFORE":
                return ON_OR_BEFORE;
            default:
                throw new IllegalArgumentException("Invalid ComparisonOperator: " + text);
        }
    }
}
