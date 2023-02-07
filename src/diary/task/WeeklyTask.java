package diary.task;

import diary.Type;
import diary.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Еженедельная задача
 */
public class WeeklyTask extends Task {
    public WeeklyTask(String title, String description, LocalDateTime dateTime, Type type) throws IncorrectArgumentException {
        super(title, description, dateTime, type);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return (localDate.isEqual(getDateTime().toLocalDate()) || localDate.isAfter(getDateTime().toLocalDate())) &&
                localDate.getDayOfWeek().equals(getDateTime().toLocalDate().getDayOfWeek());
    }
}