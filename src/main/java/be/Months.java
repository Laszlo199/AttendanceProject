package be;

/**
 * @author Kuba
 * @date 3/30/2021 5:40 PM
 */
public enum Months {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    private final int value;

    private Months(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
