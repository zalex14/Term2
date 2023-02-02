package diary.task;

import diary.Type;
import diary.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Ежедневная задача
 */
public class DailyTask extends Task {
    public DailyTask(String title, String description, LocalDateTime dateTime, Type type) throws IncorrectArgumentException {
        super(title, description, dateTime, type);
    }

    // метод для получения следующей даты и времени выполнения
    @Override
    public boolean appearsIn(LocalDateTime dateTime) {
        return (dateTime.toLocalDate()).equals(LocalDate.now().plusDays(1));
    }
}