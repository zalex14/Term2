package diary.service;

import diary.exception.IncorrectArgumentException;
import diary.exception.TaskNotFoundException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {

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
                            TaskService.addTask(sc);
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
                        LocalDate date = LocalDate.parse(searchDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
}