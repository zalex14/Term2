package diary;

/**
 * повторяемость задачи
 */
public enum Repeatability {
    ONETIME("однократная"),
    DAILY("ежедневная"),
    WEEKLY("еженедельная"),
    MONTHLY("ежемесячная"),
    YEARLY("ежегодная");
    private final String repeatability;

    Repeatability(String repeatability) {
        this.repeatability = repeatability;
    }

    @Override
    public String toString() {
        return " Повторяемость " + repeatability;
    }
}