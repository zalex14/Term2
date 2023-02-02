package diary.task;

import diary.Type;
import diary.exception.IncorrectArgumentException;

import java.time.LocalDateTime;

/**
 * Однократная задача
 */
public class OneTimeTask extends Task {
    public OneTimeTask(String title, String description, LocalDateTime dateTime, Type type) throws IncorrectArgumentException {
        super(title, description, dateTime, type);
    }

    @Override
    public boolean appearsIn(LocalDateTime dateTime) {
        return false;
    }
}