package diary;

/**
 * Тип задачи : Личная или рабочая
 */
public enum Type {
    WORK("Рабочая"),
    PERSONAL("Личная");
    private final String type;

    Type(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return " Тип задачи " + type;
    }
}