package GameLib;

public enum Util {
    WIDTH(480),
    HEIGHT(720),
    KEY_UP(0),
    KEY_DOWN(1),
    KEY_LEFT(2),
    KEY_RIGHT(3),
    KEY_CONTROL(4),
    KEY_ESCAPE(5),
    INACTIVE(0),
    ACTIVE(1),
    EXPLODE(2);

    private final int value;

    // Constructor
    Util(int value) {
        this.value = value;
    }

    public int Value() {
        return value;
    }
}