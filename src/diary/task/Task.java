package diary.task;

import diary.Type;
import diary.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Базовый класс задачи
 * задача: заголовок, описание, тип (рабочая/личная), дата и время создания, id (через генератор). признак повторяемости (), метод следующего исполнения
 */
public abstract class Task {
    private static int idGenerator = 0;
    private final int id;                   // id задачи
    private String title;                   // заголовок задачи
    private String description;             // описание задачи
    private final LocalDateTime dateTime;   // дата и время задачи
    private Type type;                      // тип задачи (рабочая/личная)

    public Task(String title, String description, LocalDateTime dateTime, Type type) throws IncorrectArgumentException {
        this.id = idGenerator++;
        setTitle(title);
        setDescription(description);
        this.dateTime = dateTime;
        setType(type);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    // Заголовок, описание, тип и признак повторяемости обязательны к заполнению
    public void setTitle(String title) throws IncorrectArgumentException {
        if (title == null || title.isBlank()) {
            throw new IncorrectArgumentException("Заголовок задачи обязателен к заполнению");
        } else {
            this.title = title;
        }
    }

    public String getDescription() {
        return description;
    }

    // Заголовок, описание, тип и признак повторяемости обязательны к заполнению
    public void setDescription(String description) throws IncorrectArgumentException {
        if (description == null || description.isBlank()) {
            throw new IncorrectArgumentException("Описание задачи обязательно к заполнению");
        } else {
            this.description = description;
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Type getType() {
        return type;
    }

    // Заголовок, описание, тип и признак повторяемости обязательны к заполнению
    public void setType(Type type) throws IncorrectArgumentException {
        if (type == null) {
            throw new IncorrectArgumentException("Тип задачи обязателен к заполнению");
        } else {
            this.type = type;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() && getTitle().equals(task.getTitle()) && getDescription().equals(task.getDescription()) && getDateTime().equals(task.getDateTime()) && getType() == task.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getDateTime(), getType());
    }

    @Override
    public String toString() {
        return "  Задача " + id +
                " Заголовок: " + title +
                " Описание: " + description +
                " " + type +
                " Дата и время выполнения: " + dateTime + "\n";
        //  LocalDate.parse(searchDateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy hh.mm"))
    }

    // метод для получения следующей даты и времени выполнения
    public abstract boolean appearsIn(LocalDate localDate);
}