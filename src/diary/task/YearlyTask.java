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
        // учитываем високосный год, 29 февраля исполняется только в високосном году
        return !localDate.isBefore(getDateTime().toLocalDate()) && ((localDate.isLeapYear() && (localDate.getDayOfMonth() == getDateTime().toLocalDate().getDayOfMonth() && localDate.getMonthValue() == getDateTime().toLocalDate().getMonthValue())) || !localDate.isLeapYear() && (localDate.getDayOfYear() == getDateTime().toLocalDate().getDayOfYear()));
    }
}