package diary.exception;

/**
 * Исключение несуществующей задачи
 */
public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
        super(" Задача не найдена и не удалена! ");
    }
}