package diary.service;

import diary.Type;
import diary.exception.IncorrectArgumentException;
import diary.exception.TaskNotFoundException;
import diary.task.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Метод: заносить задачу, удалять задачу, получать список на предстоящий день
 * Работа с задачами в виде отдельного класса - сервиса
 */
public class TaskService {
    private static Map<Integer, Task> taskMap = new HashMap<>();
    private static Collection<Task> removedTasks = new HashSet<>();

    // Добавляем задачу в ежедневник
    public static void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    // удаляем задачу по id
    public static void remove(int taskId) throws TaskNotFoundException {
        if (taskMap.containsKey(taskId)) {
            removedTasks.add(taskMap.get(taskId));
            taskMap.remove(taskId);
        } else {
            throw new TaskNotFoundException();
        }
    }

    public static String getRemovedTasks() {
        return removedTasks.toString();
    }

    // Получаем задачи на день
    public static Collection<Task> getAllByDate(LocalDate localDate) {
        Collection<Task> tasksToDate = new ArrayList<>();
        for (Map.Entry<Integer, Task> task : taskMap.entrySet()) {
            // назначенные задачи на эту дату и задачи, повторяющиеся на эту дату
            if (task.getValue().appearsIn(localDate)
            ) {
                tasksToDate.add(task.getValue());
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
        }
        return groupeByDateMap;
    }

    //  редактируем заголовок ранее созданной задачи
    public static void updateTitle(int taskId, String taskTitle) throws TaskNotFoundException, IncorrectArgumentException {
        if (taskMap.containsKey(taskId)) {
            taskMap.get(taskId).setTitle(taskTitle);
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

    static void addTask(Scanner sc) throws IncorrectArgumentException, ParseException {
        System.out.println(" Введите заголовок задачи: ");
        String taskTitle = sc.next();
        if (taskTitle.isEmpty()) {          // Проверяем ввод
            System.out.println(" Заголовок задачи обязателен! ");
        }

        System.out.println(" Введите описание задачи: ");
        String taskDescription = sc.next();
        if (taskDescription.isEmpty()) {         // Проверяем ввод
            System.out.println(" Описание задачи обязательно! ");
        }

        System.out.println(" Введите тип задачи: P - личная, W - рабочая (латинские буквы)");
        Type type = null;
        String selectTaskType = sc.nextLine(); // "[PWpw]"
        if (selectTaskType.equalsIgnoreCase("P")) {
            type = Type.PERSONAL;
        } else if (selectTaskType.equalsIgnoreCase("W")) {
            type = Type.WORK;
        } else {
            System.out.println(" Некоректный ввод: задача может быть только либо личная (P), либо рабочая (W) латинские буквы ");
        }

        System.out.println(" Введите дату и время задачи id формате: dd.MM.yyyy hh.mm");
        String searchDateTime = sc.next(); // "dd.MM.yyyy hh.mm"
        LocalDate dateTime = LocalDate.parse(searchDateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy hh.mm"));

        System.out.println(" Введите повторяемость задачи: О - однократно, D - ежедневно, W - еженедельно, M - ежемесячно, Y - ежегодно (латинскиу буквы) ");
        String repeatTask = sc.next(); // "[ODWMYodwmy]"
        if (repeatTask.equalsIgnoreCase("O")) {
            TaskService.addTask(new OneTimeTask(taskTitle, taskDescription, dateTime.atStartOfDay(), type));
        } else if (repeatTask.equalsIgnoreCase("D")) {
            TaskService.addTask(new DailyTask(taskTitle, taskDescription, dateTime.atStartOfDay(), type));
        } else if (repeatTask.equalsIgnoreCase("W")) {
            TaskService.addTask(new WeeklyTask(taskTitle, taskDescription, dateTime.atStartOfDay(), type));
        } else if (repeatTask.equalsIgnoreCase("M")) {
            TaskService.addTask(new MonthlyTask(taskTitle, taskDescription, dateTime.atStartOfDay(), type));
        } else if (repeatTask.equalsIgnoreCase("Y")) {
            TaskService.addTask(new YearlyTask(taskTitle, taskDescription, dateTime.atStartOfDay(), type));
        } else {
            System.out.println(" Некоректный ввод: проверьте повторяемость: O, D, W, M, Y латинскими буквами ");
        }
    }
}