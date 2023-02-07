package diary.task;

import diary.Type;
import diary.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Ежегодная задача
 */
public class YearlyTask extends Task {
    public YearlyTask(String title, String description, LocalDateTime dateTime, Type type) throws IncorrectArgumentException {
        super(title, description, dateTime, type);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return (localDate.isEqual(getDateTime().toLocalDate()) || localDate.isAfter(getDateTime().toLocalDate())) &&
                (localDate.getDayOfYear() == getDateTime().toLocalDate().getDayOfYear());
    }
}