package diary.service;

import diary.Type;
import diary.exception.IncorrectArgumentException;
import diary.exception.TaskNotFoundException;
import diary.task.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {
    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy hh.mm");

    public static void selectMenu() {
        Scanner sc = new Scanner(System.in);

        label:
        while (true) {
            System.out.println("\n Выберите пункт меню для задачи ежедневника:\n 1 - Добавить \n 2- Удалить \n 3- Получить на день " +
                    "\n 4- Редактировать заголовок \n 5- Редактировать описание  \n 6- Сгруппировать по датам \n 7- Вывести удаленные задачи \n 0 - Выйти");
            if (sc.hasNextInt()) {
                int menu = sc.nextInt(); // Integer.parseInt("\\d{1}")
                switch (menu) {
                    case 1:
                        try {
                            addTask(sc);
                        } catch (IncorrectArgumentException | ParseException e) {
                            System.out.println(" Задача не добавлена " + e);
                        }
                        break;
                    case 2:
                        System.out.println(" Введите id задачи: ");
                        int taskId = sc.nextInt();
                        try {
                            TaskService.remove(taskId);
                        } catch (TaskNotFoundException e) {
                            System.out.println(" Задача не найдена " + e);
                        }
                        break;
                    case 3:
                        System.out.println(" Введите дату в формате dd.MM.yyyy");
                        String searchDate = sc.next();  // "dd.MM.yyyy"
                        LocalDate date = LocalDate.parse(searchDate, dateFormat);
                        System.out.println(TaskService.getAllByDate(date));
                        break;
                    case 4:
                        System.out.println(" Редактировать заголовок. Введите id задачи ");
                        int id = sc.nextInt();
                        System.out.println(" Введите заголовок задачи ");
                        String title = sc.next();
                        try {
                            TaskService.updateTitle(id, title);
                        } catch (TaskNotFoundException | IncorrectArgumentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        System.out.println(" Редактировать описание. Введите id задачи ");
                        int editId = sc.nextInt();
                        System.out.println(" Введите описание задачи ");
                        String description = sc.next();
                        try {
                            TaskService.updateDescription(editId, description);
                        } catch (TaskNotFoundException | IncorrectArgumentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        System.out.println(" Сгрупировать задачи по датам " + TaskService.getAllGroupeByDate());
                        break;
                    case 7:
                        System.out.println(" Вывести удаленные задачи " + TaskService.getRemovedTasks());
                        break;
                    case 0:
                        break label;
                }
            } else {
                sc.next();
                System.out.println(" Выберите пункт меню из списка ! ");
            }
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
        LocalDate dateTime = LocalDate.parse(searchDateTime, dateTimeFormat);

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