package diary.exception;

/**
 * Исключение некорректного аргумента задачи
 */
public class IncorrectArgumentException extends Exception {
    private final String argument;

    public IncorrectArgumentException(String argument) {
        this.argument = argument;
    }

    @Override
    public String toString() {
        return " IncorrectArgumentException: " + argument;
    }
}