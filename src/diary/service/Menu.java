package diary.service;

import diary.Type;
import diary.exception.IncorrectArgumentException;
import diary.exception.TaskNotFoundException;
import diary.task.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;

public class Menu {

    public static void SelectMenu() {
        Scanner sc = new Scanner(System.in);

        {
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
                                throw new RuntimeException(e);
                            }
                            break;
                        case 2:
                            System.out.println(" Введите id задачи: ");
                            int taskId = sc.nextInt();
                            try {
                                TaskService.remove(taskId);
                            } catch (TaskNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 3:
                            System.out.println(" Введите дату в формате dd.MM.yyyy");
                            String searchDate = sc.next();  // "dd.MM.yyyy"
                            SimpleDateFormat dateFormatted = new SimpleDateFormat("dd.MM.yyyy");
                            try {
                                LocalDate date = LocalDate.ofInstant(dateFormatted.parse(searchDate).toInstant(), ZoneId.systemDefault());
                                System.out.println(TaskService.getAllByDate(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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
                            System.out.println(" Сгрупировать задачи по датам. Введите любую цифру от 0 до 9");
                            int start = sc.nextInt();
                            if (start > 0 && start < 10) {
                                TaskService.getAllGroupeByDate();
                            }
                            break;
                        case 7:
                            System.out.println(" Вывести удаленные задачи. Введите любую цифру от 0 до 9");
                            start = sc.nextInt();
                            if (start > 0 && start < 10) {
                                TaskService.getRemovedTasks();
                            }
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
    }

    private static void addTask(Scanner sc) throws IncorrectArgumentException, ParseException {

        System.out.println(" Введите заголовок задачи: ");
        String taskTitle = sc.next();
        if (taskTitle.isEmpty()) {          // Проверяем ввод
            System.out.println(" Заголовок задачи обязателен! ");
            sc.close();
        }


        System.out.println(" Введите описание задачи: ");
        String taskDescription = sc.next();
        if (taskDescription.isEmpty()) {         // Проверяем ввод
            System.out.println(" Описание задачи обязательно! ");
            sc.close();
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
            sc.close();
        }

        System.out.println(" Введите дату и время задачи d формате: dd.MM.yyyy hh.mm");
        String searchDateTime = sc.next("dd.MM.yyyy hh.mm");
        SimpleDateFormat dateTimeFormatted = new SimpleDateFormat("dd.MM.yyyy hh.mm");
        LocalDateTime dateTime = LocalDateTime.ofInstant(dateTimeFormatted.parse(searchDateTime).toInstant(), ZoneId.systemDefault());

//        GregorianCalendar dateTime = new GregorianCalendar(year, month, day, hour, minute);

        System.out.println(" Введите повторяемость задачи: О - однократно, D - ежедневно, W - еженедельно, M - ежемесячно, Y - ежегодно (латинскиу буквы) ");
        String repeatTask = sc.next("[ODWMYodwmy]");
        if (repeatTask.equalsIgnoreCase("O")) {
//            String repeatability = String.valueOf(Repeatability.ONETIME);
            TaskService.addTask(new OneTimeTask(taskTitle, taskDescription, dateTime, type));
        } else if (repeatTask.equalsIgnoreCase("D")) {
            TaskService.addTask(new DailyTask(taskTitle, taskDescription, dateTime, type));
        } else if (repeatTask.equalsIgnoreCase("W")) {
            TaskService.addTask(new WeeklyTask(taskTitle, taskDescription, dateTime, type));
        } else if (repeatTask.equalsIgnoreCase("M")) {
            TaskService.addTask(new MonthlyTask(taskTitle, taskDescription, dateTime, type));
        } else if (repeatTask.equalsIgnoreCase("Y")) {
            TaskService.addTask(new YearlyTask(taskTitle, taskDescription, dateTime, type));
        } else {
            System.out.println(" Некоректный ввод: проверьте повторяемость: O, D, W, M, Y латинскими буквами ");
            sc.close();
        }
    }
}