package diary.service;

import diary.exception.IncorrectArgumentException;
import diary.exception.TaskNotFoundException;
import diary.task.Task;

import java.time.LocalDate;
import java.util.*;

/**
 * Метод: заносить задачу, удалять задачу, получать список на предстоящий день
 * Работа с задачами в виде отдельного класса - сервиса
 */
public class TaskService {
    private static final Map<Integer, Task> taskMap = new HashMap<>();
    private static final Collection<Task> removedTasks = new HashSet<>();

    // Добавляем задачу в ежедневник
    public static void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    // удаляем задачу по id
    public static void remove(int taskId) throws TaskNotFoundException {
        if (taskMap.containsKey(taskId) && taskMap.get(taskId) != null) {
            Task task = taskMap.get(taskId);
            removedTasks.add(task);
            taskMap.remove(taskId);
//            System.out.println(" Задача " + taskId + " удалена ");
        } else {
            throw new TaskNotFoundException();
        }
    }

    // Получаем задачи на день
    public static Collection<Task> getAllByDate(LocalDate localDate) {
        Collection<Task> tasksToDate = new ArrayList<>();
        for (Map.Entry<Integer, Task> task : taskMap.entrySet()) {
            // назначенные задачи на эту дату и задачи, повторяющиеся на эту дату
            if (task.getValue().getDateTime().toLocalDate().equals(localDate) || task.getValue().appearsIn(localDate.atStartOfDay())
            ) {
                tasksToDate.add(task.getValue());
                System.out.println(task.getValue());
            }
        }
        return tasksToDate;
    }

    public static Map<LocalDate, Collection<Task>> getAllGroupeByDate() {
        Map<LocalDate, Collection<Task>> groupeByDateMap = new TreeMap<>();
        for (Map.Entry<Integer, Task> task : taskMap.entrySet()) {
            LocalDate localDate = task.getValue().getDateTime().toLocalDate();
            System.out.println(" Задачи на " + localDate);
            if (!groupeByDateMap.containsKey(localDate)) {
                groupeByDateMap.put(localDate, new ArrayList<>());
            }
            groupeByDateMap.get(localDate).add(task.getValue());
            System.out.println(task.getValue());
        }
        return groupeByDateMap;
    }

    //  редактируем заголовок ранее созданной задачи
    public static void updateTitle(int taskId, String taskTitle) throws TaskNotFoundException, IncorrectArgumentException {
        System.out.println(" Задача " + taskId + "  до редактирования заголовка " + taskMap.get(taskId));
        if (taskMap.containsKey(taskId) && taskMap.get(taskId) != null) {
            taskMap.get(taskId).setTitle(taskTitle);
            System.out.println(" Задача " + taskId + " после редактирования заголовка " + taskMap.get(taskId));
        } else {
            throw new TaskNotFoundException();
        }
    }

    // редактируем описание ранее созданной задачи
    public static void updateDescription(int taskId, String taskDescription) throws TaskNotFoundException, IncorrectArgumentException {
        System.out.println(" Задача " + taskId + "  до редактирования описания " + taskMap.get(taskId));
        if (taskMap.containsKey(taskId) && taskMap.get(taskId) != null) {
            taskMap.get(taskId).setDescription(taskDescription);
            System.out.println(" Задача " + taskId + "  после редактирования описания " + taskMap.get(taskId));

        } else {
            throw new TaskNotFoundException();
        }
    }

    public static Collection<Task> getRemovedTasks() {
        return removedTasks;
    }
}